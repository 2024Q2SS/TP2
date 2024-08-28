import os
import json
from PIL import Image, ImageDraw, ImageColor
from mpl_toolkits.mplot3d import Axes3D
from mpl_toolkits.mplot3d.art3d import Poly3DCollection
import matplotlib.pyplot as plt
from matplotlib.colors import to_rgba
import numpy as np

# Function to draw the grid in 2D based on the current state
def draw_grid_2d(cells, size):
    img = Image.new("RGB", (size, size), color="white")
    draw = ImageDraw.Draw(img)

    cell_size = size // grid_size
    vibrant_purple = ImageColor.getrgb("#800080")  # More vibrant purple
    vibrant_yellow = ImageColor.getrgb("#FFFF00")  # Vibrant yellow
    
    for cell in cells:
        x = cell["coordinates"]["x"] * cell_size
        y = cell["coordinates"]["y"] * cell_size

        if cell["state"] == "ALIVE":
            # Calculate the proximity to the nearest border
            dist_to_border = min(cell["coordinates"]["x"], cell["coordinates"]["y"],
                                 grid_size - cell["coordinates"]["x"] - 1, grid_size - cell["coordinates"]["y"] - 1)

            # Normalize the distance to a 0-1 range
            max_distance = grid_size // 2
            normalized_dist = dist_to_border / max_distance

            # Map the normalized distance to a color between vibrant purple and yellow
            color = tuple(int(a + normalized_dist * (b - a)) for a, b in zip(vibrant_purple, vibrant_yellow))
        else:
            color = "white"

        draw.rectangle([x, y, x + cell_size, y + cell_size], fill=color)
        draw.rectangle([x, y, x + cell_size, y + cell_size], outline="gray")  # draw the cell boundaries

    return img

# Function to draw the grid in 3D based on the current state
def draw_grid_3d(cells, size):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    
    vibrant_purple = np.array(ImageColor.getrgb("#800080")) / 255.0  # Normalize RGB to 0-1 range
    vibrant_yellow = np.array(ImageColor.getrgb("#FFFF00")) / 255.0  # Normalize RGB to 0-1 range
    
    for cell in cells:
        if cell["state"] == "ALIVE":
            x = cell["coordinates"]["x"]
            y = cell["coordinates"]["y"]
            z = cell["coordinates"]["z"]

            # Calculate the proximity to the nearest border
            dist_to_border = min(x, y, z, grid_size - x - 1, grid_size - y - 1, grid_size - z - 1)

            # Normalize the distance to a 0-1 range
            max_distance = grid_size // 2
            normalized_dist = dist_to_border / max_distance

            # Map the normalized distance to a color between vibrant purple and yellow
            color = vibrant_purple + normalized_dist * (vibrant_yellow - vibrant_purple)
            color = to_rgba(color)  # Ensure it's in the correct RGBA format

            # Draw the cell
            ax.bar3d(x, y, z, 1, 1, 1, color=[color])
    
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

