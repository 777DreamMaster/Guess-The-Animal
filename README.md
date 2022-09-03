# Guess-The-Animal

## Details
Player thinks of an animal and computer tries to guess what it is based on the answers. During the game, the computer will extend its knowledge base by learning new facts about animals and using this information in the next game. All information keeped in the process will be saveed in one of the following formats: JSON, XML, YAML (To choose the type you can add "-type json" as an argument (XML is standart)).
Program alowed not only play the guessing game but also:
- List of all animals
- Search for an animal
- Calculate statistics
- Print the Knowledge Tree  

The app is available not only in English. but also in Esperanto.
## Example
The game starts anew
```
Good evening!

I want to learn about animals.
Which animal do you like most?
> cat

Welcome to the animal expert system!

What do you want to do:

1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
```
An already existing knowledge base has been loaded
```
Good afternoon!

Welcome to the animal expert system!

What do you want to do:

1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
```
The user wants to play a game.
```
> 1
You think of an animal, and I guess it.
Press enter when you're ready.

It is a mammal?
> yes
It is living in the forest?
> yes
It is a shy animal?
> no
Is it a wolf?
> no
I give up. What animal do you have in mind?
> bear
Specify a fact that distinguishes a wolf from a bear.
The sentence should be of the format: It can/has/is ....
> It can hibernate
Is the statement correct for a bear?
> yeah
I have learned the following facts about animals:
 - The wolf can't hibernate.
 - The bear can hibernate.
I can distinguish these animals by asking the question:
 - Can it hibernate?
Wonderful! I've learned so much about animals!
Do you want to play again?
```
The user wants to see the list of all animals.
```
> 2
Here are the animals I know:
 - cat
 - dog
 - fox
 - hare
 - shark
 - wolf
```
The user wants to search for an animal.
```
> 3
Enter the animal:
hare
Facts about the hare:
 - It is a mammal.
 - It is living in the forest.
 - It doesn't have a long bushy tail.
 - It is a shy animal.
```
If the animal is not found, print this information for the user:
```
> 3
Enter the animal:
rabbit
No facts about the rabbit.
```
The user wants to see the stats.
```
> 4
The Knowledge Tree stats

- root node                    It is a mammal
- total number of nodes        11
- total number of animals      6
- total number of statements   5
- height of the tree           4
- minimum animal's depth       1
- average animal's depth       3.0
```
The user wants to print tree.
```
> 5

 └ Is it a mammal?
  ├ Is it living in the forest?
  │├ Is it a shy animal?
  ││├ a hare
  ││└ a wolf
  │└ Can it climb tree?
  │ ├ a cat
  │ └ a dog
  └ a shark
```
