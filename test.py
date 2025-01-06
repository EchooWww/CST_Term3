coordinates = [(x, y) for x in range(3) for y in range(2)]
print(coordinates)


names = [["ALICE", "BOB"], ["TOM"], ["CHARLIE", "DAVID"]]
upper_long_names = [
    [name.upper() for name in sublist if len(name) > 3] for sublist in names
]
print(upper_long_names)

matrix = [[(i, j) for j in range(3)] for i in range(4)]
print(matrix)
