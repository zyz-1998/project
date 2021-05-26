import argparse

import torch
import torch.nn.parallel
import datasets
from utils import AverageMeter, img_cvt
import soft_renderer as sr
import soft_renderer.functional as srf
import models
import time
import os
import imageio
import numpy as np

BATCH_SIZE = 100
IMAGE_SIZE = 64

PRINT_FREQ = 100
SAVE_FREQ = 100

MODEL_DIRECTORY = './data/models'
DATASET_DIRECTORY = './data/datasets'

SIGMA_VAL = 0.01
IMAGE_PATH = ''

# arguments
parser = argparse.ArgumentParser()
parser.add_argument('-eid', '--experiment-id', type=str)
parser.add_argument('-d', '--model-directory', type=str, default=MODEL_DIRECTORY)
parser.add_argument('-dd', '--dataset-directory', type=str, default=DATASET_DIRECTORY)

parser.add_argument('-is', '--image-size', type=int, default=IMAGE_SIZE)
parser.add_argument('-bs', '--batch-size', type=int, default=BATCH_SIZE)
parser.add_argument('-img', '--image-path', type=str, default=IMAGE_PATH)

parser.add_argument('-sv', '--sigma-val', type=float, default=SIGMA_VAL)

parser.add_argument('-pf', '--print-freq', type=int, default=PRINT_FREQ)
parser.add_argument('-sf', '--save-freq', type=int, default=SAVE_FREQ)
args = parser.parse_args()

# setup model & optimizer
model = models.Model('data/obj/sphere/sphere_642.obj', args=args)
model = model.cuda()

state_dicts = torch.load(args.model_directory)
model.load_state_dict(state_dicts['model'], strict=False)
model.eval()

def test():
    end = time.time()

    batch_time = AverageMeter()
    data_time = AverageMeter()
    losses = AverageMeter()
    losses1 = AverageMeter()

    demo_img_size = 11
    demo_img = torch.zeros(demo_img_size, 4, 64, 64, dtype=torch.uint8) 
    for i in range(0,demo_img.size(0)):
        img = imageio.imread('/home/zyz/Project/SoftRas/examples/recon/demo/'+'%02d.png' % i) 
        print('/home/zyz/Project/SoftRas/examples/recon/demo/'+'%02d.png' % i)
        demo_img[i] = torch.transpose(torch.from_numpy(img), 0, 2)
    demo_img = demo_img.float()
    demo_img /= 255.0

    images = torch.autograd.Variable(demo_img).cuda()

    vertices, faces, textures = model(images, task='generate')

    textures = textures.clone().cuda().data.cpu().numpy()
    textures = np.ascontiguousarray(textures)
    textures = torch.from_numpy(textures)
    
    batch_time.update(time.time() - end)
    end = time.time()

    # save demo images
    for k in range(vertices.size(0)):
        mesh_path = os.path.join('/home/zyz/Project/SoftRas/examples/recon/demo/', '%06d.obj' % k)
        input_path = os.path.join('/home/zyz/Project/SoftRas/examples/recon/demo/', '%06d_input.png' % k)
        srf.save_obj(mesh_path, vertices[k], faces[k],textures[k])
        imageio.imsave(input_path, img_cvt(images[k]))
            #  imageio.imsave(os.path.join(directory_mesh_cls, '%07d_render_images_test.png' % obj_id), img_cvt(render_images_test[0][0]))
    print("finish")
test()
