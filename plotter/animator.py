import os
import json
from PIL import Image, ImageDraw

# Function to draw the grid based on the current state
def draw_grid(cells, size):
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

# Load the config file
with open("../config.json", "r") as f:
    config = json.load(f)

grid_size = config["size"]  # size of the grid (e.g., 50x50)
canvas_size = 500  # define the size of the image canvas

# List of images for the GIF
images = []

# Load each JSON file and create an image
output_folder = "../GOF/output"
files = sorted([f for f in os.listdir(output_folder) if f.startswith("frame") and f.endswith(".json")],
               key=lambda x: int(x.replace("frame", "").replace(".json", "")))

for file in files:
    with open(os.path.join(output_folder, file), "r") as f:
        cells = json.load(f)
    img = draw_grid(cells, canvas_size)
    images.append(img)

# Save the images as a GIF
images[0].save("game_of_life.gif", save_all=True, append_images=images[1:], duration=300, loop=0)

print("GIF saved as game_of_life.gif")

