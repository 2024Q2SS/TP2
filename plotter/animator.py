import os
import json
from PIL import Image, ImageDraw
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

# Function to draw the grid in 2D based on the current state
def draw_grid_2d(cells, size):
    img = Image.new("RGB", (size, size), color="white")
    draw = ImageDraw.Draw(img)

    cell_size = size // grid_size
    for cell in cells:
        x = cell["coordinates"]["x"] * cell_size
        y = cell["coordinates"]["y"] * cell_size
        color = "black" if cell["state"] == "ALIVE" else "white"
        draw.rectangle([x, y, x + cell_size, y + cell_size], fill=color)
        draw.rectangle([x, y, x + cell_size, y + cell_size], outline="gray")  # draw the cell boundaries

    return img

# Function to draw the grid in 3D based on the current state
def draw_grid_3d(cells, size):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    
    x_vals = [cell["coordinates"]["x"] for cell in cells if cell["state"] == "ALIVE"]
    y_vals = [cell["coordinates"]["y"] for cell in cells if cell["state"] == "ALIVE"]
    z_vals = [cell["coordinates"]["z"] for cell in cells if cell["state"] == "ALIVE"]
    
    ax.scatter(x_vals, y_vals, z_vals, c='black', marker='s')

    ax.set_xlim([0, grid_size])
    ax.set_ylim([0, grid_size])
    ax.set_zlim([0, grid_size])
    
    ax.set_box_aspect([1,1,1])  # Aspect ratio is 1:1:1
    
    plt.close(fig)
    
    # Convert the matplotlib figure to a PIL image
    fig.canvas.draw()
    img = Image.frombytes('RGB', fig.canvas.get_width_height(), fig.canvas.tostring_rgb())

    return img

# Load the config file
with open("../config.json", "r") as f:
    config = json.load(f)

grid_size = config["size"]  # size of the grid (e.g., 50x50)
canvas_size = 500  # define the size of the image canvas
dimensions = config["dimensions"]  # either 2 or 3

# List of images for the GIF
images = []

# Load each JSON file and create an image
output_folder = "../GOF/output"
files = sorted([f for f in os.listdir(output_folder) if f.startswith("frame") and f.endswith(".json")],
               key=lambda x: int(x.replace("frame", "").replace(".json", "")))

for file in files:
    with open(os.path.join(output_folder, file), "r") as f:
        cells = json.load(f)
    if dimensions == 2:
        img = draw_grid_2d(cells, canvas_size)
    elif dimensions == 3:
        img = draw_grid_3d(cells, canvas_size)
    images.append(img)

# Save the images as a GIF
images[0].save("game_of_life.gif", save_all=True, append_images=images[1:], duration=300, loop=0)

print("GIF saved as game_of_life.gif")

