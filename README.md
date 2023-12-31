# K-D-Tree
Comparative Analysis of Running Time and Accuracy as a Function of the Number of Training Instances: Kd-Tree

I.	INTRODUCTION 

The topic at hand is comparing the K-D Tree and Ball Tree data structures' performance and accuracy for closest neighbour search in a classification task utilising the Breast Cancer Wisconsin (Diagnostic) dataset. The dataset includes records of breast cancer diagnosis and ten attributes associated to the diagnosis. The objective is to train a model using a subset of N training records and verify its accuracy using a different subset of T testing records. Records are chosen at random for each run's training and testing while the ratio of N to T is set at 4. The proportion of accurately predicted diagnoses in the test set is used to gauge the model's accuracy. The actual execution time on the same computer under the same circumstances is used to calculate the testing phase's running time. The training records is stored in the selected data structure, either a K-D Tree or a Ball Tree, and the k (with k = 3, 5, and 7) nearest neighbours are retrieved for classification using a majority vote and median attribute values. The goal is to contrast the two data structures' running times and accuracy under the given circumstances.

II.	DESCRIPTION AND RESULTS OF THE K-D TREE

K-D trees are binary trees in which every node is a k-dimensional point. K-D trees are used for efficient partitioning and nearest neighbor search [1]. The program uses HashMaps to organize the Breast Cancer Wisconsin dataset. In the HashMaps, the record Identifications (IDs) are used as the key while their respective values are stored in arrays which contain the diagnosis and the ten attributes. In total, three HashMaps are used to organize the dataset. One HashMap is used to organize all records, another one for the N training records and the final one for the T testing records. The training and testing records HashMaps omitted the diagnosis in their key-value pairs. The K-D tree was built using the records in the training records HashMap. In the K-D tree, the root node represented the record that contained the median value of the first attribute. The remaining records were partitioned recursively based on the median values of the following attributes. 

A priority queue was utilized when finding the k-nearest neighbours of a given test record (target). The priority queue was implemented in such a way that the nearest neighbours were prioritized based on their Euclidean distance to the target. The distance method implemented in the KdTree class was used as the comparator to determine the priority. The searching algorithm to find the k-nearest neighbours was recursive and the algorithm extracted k nodes with the highest priority from the priority queue. In terms of running time and testing accuracy, both Figures 1 and 2 demonstrate their respective trends as a function of the number of training instances. When measuring the running time and accuracy for the instances and the k nearest neighbors, the data was gathered under the same conditions. In addition, for each N (training instances), the testing accuracy and running time values were averages of 8 runs of the program. 

As is demonstrated by Figure 1, the testing accuracy remained consistent regardless of the number of training instances. When the k nearest neighbors were seven, the program was the most accurate at predicting a diagnosis. When the k nearest neighbors were three, the testing accuracy was the lowest, however it still remained above eighty percent. As is demonstrated by Figure 2, when the k nearest neighbors were three, the running time increased logarithmic. When the k nearest neighbors were both seven and five, the running times increased linearly. As can be observed by the figure, the smaller the k nearest neighbor value, the higher the running time. 



![image](https://github.com/JustinSciortino/K-D-Tree/assets/123967053/fd625038-5467-445e-817a-5afad3936199)
![image](https://github.com/JustinSciortino/K-D-Tree/assets/123967053/0ed76829-d520-4321-b459-30a28a4a6bee)


