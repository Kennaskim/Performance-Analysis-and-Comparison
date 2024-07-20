import pandas as pd
import matplotlib.pyplot as plt

# Update this path to the correct location of your CSV file
csv_file_path = 'C:/Users/kennas/Desktop/Java codes/Basics/Performance Test/performance_results.csv'

# Read the CSV file
df = pd.read_csv(csv_file_path)

# Pivot the data for better visualization
pivot_df = df.pivot(index='Dataset Size', columns='Algorithm', values='Time (seconds)')

# Plot the data
pivot_df.plot(kind='bar', figsize=(14, 8))
plt.title('Sorting Algorithm Performance Comparison')
plt.xlabel('Dataset Size')
plt.ylabel('Time (seconds)')
plt.yscale('log')  # Use a logarithmic scale for better visualization of large differences
plt.xticks(rotation=0)
plt.legend(title='Algorithm')
plt.grid(True, which='both', linestyle='--', linewidth=0.5)

# Save the plot to a file
plt.savefig('sorting_performance_comparison.png')

# Show the plot
plt.show()
