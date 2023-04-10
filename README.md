# Scuffed Civ

# How To Play

### 1. Open the game
The game can be opened by running the `Window.java` file in the `src` folder.
Select the `Window` class and click the green play button.

### 2. Main Menu Screen
The main menu screen will appear. Click on the "New Game" button to start the game.

If you have played the game before, you can click on the "Continue" button to load your previous game.

Online button to play the game online with other players. This currently only works with players on the same Wifi network.

Settings button to change the game settings.

Exit button to close the game.

### 2.5. Online menu
If you chose online in the main menu, you are prompted to either Host Game or Join Game.

### 3a. Choose the game settings (new game or host game)
Choose the size of the board. Each number represents a single tile increasing the board size.

Choose the number of players. The maximum number of players is 4.

Once the settings are chosen, click on the "Start" button to start the game (or server if hosting).

### 3b. Join a game (online clicked in main menu)
Input the IP of the server host, and a username.

You must then wait until all other players have connected before the game will start.

### 4. Game Screen Hub
**Top Left**: Menu button giving options.

**Top Right**: End turn button.

**Bottom Left**: A picture of your currently selected unit.

**Bottom Right**: Buttons for units you can build.

**Left Side**: Your selected units stats.

  ***1.*** Gathered materials.

  ***2.*** Unit health.

  ***3.*** Unit attack damage.

  ***4.*** Unit attack range.

  ***5.*** Unit movement range.

  ***6.*** Unit cost.

**Right Side**: Keybinds.

**Center Top**: Current players turn.

### 5. Units
#### 1. Worker
Workers are used to gather materials from the map. They can gather materials from any tile on the map except sand.
#### 2. Soldier
Soldiers are used to attack other units. They can attack any unit within their attack range.
#### 3. Building
Buildings are used to build other units.

### 6. How to win
The game ends when one player has no more units left. The player with the most units left wins the game.

### 7. How to quit the game
You can quit the game by clicking on the "Exit" button in the menu screen.

# Contributors
### 1. Cameron Walford
I worked on the saving/loading of the game, the menu and all of the menu states, and the game server, along
with other miscellaneous code throughout the project.

Saving/Loading: I made functions toJSONObject and fromJSONObject in all of the relevant GameState classes,
and these are all called in GameState's own respective JSONObject functions. This provided a convenient way
to serialize the entire gamestate so it could be saved to a .json file or sent back and forth from client
and server.

Menu: I worked on a series of MenuStates that the Menu object held by Window could be set to. This was meant to be
a fairly modular way to work with the menu, so that it would be easy to add new states as time went on.

Game Server: This is a server that works only if you're on the same wifi network as the host. It uses multithreading
to handle each client connected. When a the current player ends their turn, the server updates its own gameState and
broadcasts the updated gameState to all other clients.

### 2. Keagan Purtell
I worked on most of the core gameplay for our. This includes creating the logic behind each type of entity.
I worked on the ability to create new Entities, select them, move them, build other entities with them, collect 
resources with them and attack with Soldiers. I also created the logic behind the map generation. My personal part
of the project was creating an enemy AI that could play against the player. The ai is able to move around the map 
and build entities of both types. However I was unable to create the value logic for the AI to move towards and attack
the enemy.

### 3. Emma Merideth-Black
I worked on the logic for the User Interface, as well as designing the main menu. I created a clickable
class that runs when clicked, and from there developed easy to use methods that allowed for easy
creation of UI elements that are fully interactive, as well as being easily customizable, in order to
make development of the UI as easy as possible. I also designed the main menu, creating logic for
the buttons, and creating the images and text that are displayed on the screen, as well as previewing
map creation. The particular classes I worked in were Clickable, ClickableManager, Button, ButtonManager, 
Various Menu state classes, and the Window Class. Also, please note, due to weird git issues, my commits
are spread across the Testsnake and emmamb accounts.

### 4. Brendan Doyle
I worked on the UI that you see while playing the game. Gathering the spites and images needed for
the different panels, icons, units, backgrounds. Positioning the panels and buttons in the correct
places and loading order. I also worked on the code that allows the user to select a unit and
display the stats of that unit, the image loading for the selected unit and the many buttons 
associated with it. The in game menu that appears when you select the menu button was also my 
work. It was designed to use lambda to redirect the player to different game states.
The particular classes I worked in were Hud, HudState, InGameHud and Menuhidstate. 
