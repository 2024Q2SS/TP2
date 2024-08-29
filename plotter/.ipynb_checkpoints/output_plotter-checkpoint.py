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

# Define file patterns and labels
file_patterns = [
    ("../GOF/conway_0.2_*.csv", "Conway"),
    ("../GOF/big1_0.2_*.csv", "Big_1"),
    ("../GOF/augmented_0.2_*.csv", "Augmented")
]

# Plot each file pattern
plt.figure(figsize=(10, 6))

# Store maximum values to determine y-axis limit
max_y_values = []

for file_pattern, label in file_patterns:
    frames, smooth_cells, _ = process_files(file_pattern, label)
    plt.plot(frames, smooth_cells, label=label)
    max_y_values.append(max(smooth_cells))

# Set the limits to include (0,0) in the corner
plt.xlim(0, 1)
plt.ylim(0, max(max_y_values) * 1.1)  # Use the global max value for y-axis limit

# Labels and title
plt.xlabel("Normalized Frame")
plt.ylabel("Average Cells Alive")
plt.legend()  # Add the legend to the plot
plt.title("Average Cells Alive per Frame for Different Patterns")

plt.show()
