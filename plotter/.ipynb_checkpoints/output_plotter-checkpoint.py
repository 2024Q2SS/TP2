import glob

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.interpolate import interp1d
from scipy.ndimage import gaussian_filter1d

# Step 1: Read the CSV files
# Assuming the CSV files are named "output1.csv", "output2.csv", ..., "output5.csv"
file_pattern = "../GOF/conway_0.4_*.csv"
file_names = glob.glob(file_pattern)

# Step 2: Load the data from each file into a list
data = []
for file_name in file_names:
    df = pd.read_csv(file_name)
    cells_alive = df["average_alive_cells"].values  # Assuming the cells alive data is in the first column
    data.append(cells_alive)

# Step 3: Normalize the frames to a common scale (0 to 1)
normalized_data = []
for dataset in data:
    frames = np.linspace(0, 1, len(dataset))
    normalized_data.append((frames, dataset))

# Step 4: Define common frame points for interpolation
common_frames = np.linspace(0, 1, 100)  # 100 points from 0 to 1

# Step 5: Interpolate data to common frames
interpolated_data = []
for frames, cells in normalized_data:
    interpolation_function = interp1d(
        frames, cells, kind="linear", fill_value="extrapolate"
    )
    interpolated_data.append(interpolation_function(common_frames))

# Step 6: Average the interpolated data across datasets
average_cells = np.mean(interpolated_data, axis=0)

# Step 7: Optional: Smooth the curve
smooth_average_cells = gaussian_filter1d(average_cells, sigma=2)

# Step 8: Plot the resulting soft curve
plt.plot(common_frames, smooth_average_cells, label="Soft Curve")
plt.xlabel("Normalized Frame")
plt.ylabel("Average Cells Alive")
plt.legend()
plt.title("Average Cells Alive per Frame")
plt.show()
