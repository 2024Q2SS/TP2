import glob
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.interpolate import interp1d
from scipy.ndimage import gaussian_filter1d

# Function to process each file pattern and return interpolated and smoothed data
def process_files(file_pattern, label):
    file_names = glob.glob(file_pattern)
    data = []

    for file_name in file_names:
        df = pd.read_csv(file_name)
        cells_alive = df["average_alive_cells"].values
        data.append(cells_alive)

    normalized_data = []
    for dataset in data:
        frames = np.linspace(0, 1, len(dataset))
        normalized_data.append((frames, dataset))

    common_frames = np.linspace(0, 1, 100)

    interpolated_data = []
    for frames, cells in normalized_data:
        interpolation_function = interp1d(
            frames, cells, kind="linear", fill_value="extrapolate"
        )
        interpolated_data.append(interpolation_function(common_frames))

    average_cells = np.mean(interpolated_data, axis=0)
    smooth_average_cells = gaussian_filter1d(average_cells, sigma=2)

    return common_frames, smooth_average_cells, label

# Define file patterns and titles for each subplot
plot_details = [
    (["../GOF/conway_0.2_*.csv", "../GOF/conway_0.3_*.csv"], "Conway Patterns"),
    (["../GOF/big1_0.2_*.csv", "../GOF/big1_0.3_*.csv"], "Big_1 Patterns"),
    (["../GOF/augmented_0.2_*.csv", "../GOF/augmented_0.3_*.csv"], "Augmented Patterns")
]

# Create a grid of subplots
fig, axes = plt.subplots(len(plot_details), 1, figsize=(12, 4 * len(plot_details)))
fig.tight_layout(pad=5.0)  # Adjust space between subplots

# Plot each file pattern in its respective subplot
for ax, (file_patterns, title) in zip(axes, plot_details):
    max_y_values = []
    
    for file_pattern in file_patterns:
        frames, smooth_cells, label = process_files(file_pattern, label=None)
        ax.plot(frames, smooth_cells, label=label or file_pattern)
        max_y_values.append(max(smooth_cells))

    # Set limits and labels
    ax.set_xlim(0, 1)
    ax.set_ylim(0, max(max_y_values) * 1.1)
    ax.set_xlabel("Normalized Frame")
    ax.set_ylabel("Average Cells Alive")
    ax.set_title(title)
    ax.legend()
    ax.grid(True, linestyle='--', alpha=0.7)

# Show all plots
plt.show()
