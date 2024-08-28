import matplotlib.pyplot as plt
import pandas as pd

# Cargar el archivo CSV
df = pd.read_csv('../GOF/output.csv')

# Crear el gráfico de líneas
plt.figure(figsize=(10, 6))
plt.plot(df['Frame'], df['AverageAliveNeighbours'], marker='o')

# Personalizar el gráfico
plt.title('Average Alive Neighbours per Frame')
plt.xlabel('Frame')
plt.ylabel('Average Alive Neighbours')
plt.grid(True)

# Mostrar el gráfico
plt.show()

