import pygame
import json
import math

# Load the total number of cells and board size from the config file
with open('../config.json', 'r') as config_file:
    config = json.load(config_file)
    total_cells = config['N']
    board_size_pixels = config['Size']

# Calculate the number of cells per side (assuming a square grid)
cells_per_side = int(math.sqrt(total_cells))

# Calculate the size of each cell
cell_size = board_size_pixels // cells_per_side

# Constants
ALIVE_COLOR = (0, 0, 0)
DEAD_COLOR = (255, 255, 255)
GRID_COLOR = (200, 200, 200)  # Light grey color for grid lines

# Initialize pygame
pygame.init()
screen = pygame.display.set_mode((board_size_pixels, board_size_pixels))
pygame.display.set_caption("Cell Selection Board")

# Create grid
grid = [[0 for _ in range(cells_per_side)] for _ in range(cells_per_side)]

# Main loop
running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            x, y = pygame.mouse.get_pos()
            col = x // cell_size
            row = y // cell_size
            grid[row][col] = 1 if grid[row][col] == 0 else 0

    # Draw grid
    screen.fill(DEAD_COLOR)
    for row in range(cells_per_side):
        for col in range(cells_per_side):
            color = ALIVE_COLOR if grid[row][col] == 1 else DEAD_COLOR
            pygame.draw.rect(screen, color, (col * cell_size, row * cell_size, cell_size, cell_size))

            # Draw grid lines
            pygame.draw.rect(screen, GRID_COLOR, (col * cell_size, row * cell_size, cell_size, cell_size), 1)

    pygame.display.flip()

# Prepare data for JSON output
output_data = []
for row in range(cells_per_side):
    for col in range(cells_per_side):
        output_data.append({
            'coordinates': (row, col),
            'state': 'alive' if grid[row][col] == 1 else 'dead'
        })

# Save output to JSON file
with open('output.json', 'w') as output_file:
    json.dump(output_data, output_file, indent=4)

pygame.quit()

