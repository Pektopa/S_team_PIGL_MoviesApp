# MovieLand Application
## Useful Information
Team letter : S <br>
Team group : 3 <br>
Team members : Amina FANANI, Fabien MARCUCCINI, Batiste BORG, Numa EFREMON \
Related Subject : "Software Engineering Introduction 2023-2024"

## Overview:
The MovieLand application allows its user to browse various movies and fill
its list of favorites with the films he desires. He can also discover popular films and get further details 
about any movie he is curious about. This application can be used through two different interfaces:\
    ● The Command Line Interface (CLI).\
    ● The Graphic User Interface (GUI).

### The Command Line Interface (CLI):

#### Launch Guide:
Firstly, you should click on Gradle, then click on "runCLI" situated in /moviesapp/app/tasks/others/ as shown in the picture.

<img alt="runCLI path" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2Fe3e85e15-4fa8-48be-a982-9f910bf92edc%2FDesign_sans_titre(1).png?table=block&id=a93c7607-e27a-4060-97ca-c3489c8e6bcf&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" height="600"/>

#### Use Guide:
After launching the application, the terminal will display all the existing commands and their purposes and ask you to 
enter the number related to the command you chose.

<img alt="terminal display" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2Fa9bb68e0-77f0-4155-9755-7aed1ee25e80%2FScreenshot_2024-03-01_at_20.33.25.png?table=block&id=00616fb2-4817-4156-81dc-75841167819a&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width="800"/>

After entering the number, the terminal will either display the results of your request (like for [1] catalog) or 
ask you further questions to give you personalized results.

### The Graphic User Interface (GUI):

#### Launch Guide:
Firstly, you should click on Gradle, then click on "run" situated in /moviesapp/tasks/application/ as shown in 
the picture.

<img alt="run path" src= "https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F7f254dee-a822-49be-adfd-fa34fa2fa564%2FDesign_sans_titre.png?table=block&id=e212383b-70c4-45d5-8da3-9a266900dc02&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" height= "600">

#### Use Guide:
After launching the application, MovieLand should look like this: 

<img alt="MovieLand first display" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F0422e29d-a2c9-4db2-beb7-6f0ee3c41982%2FScreenshot_2024-03-01_at_20.50.41.png?table=block&id=62c7177f-383e-4250-b7e8-b8d1853edb78&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width="800">

The right panel of the application is used to display the movies, such as the popular movies, the favorite movies 
or the movies resulting from a search. 

While the left panel contains the name of the application "MovieLand" which, if you click on it, will display the 
popular movies.

There are also two types of research: either with a title or without one, which gives the user 
the liberty to use the one he prefers. If you choose the "Without Title" version and you want to choose two or more
genres, then you must keep clicking on "shift" or "Cmd" if you are on Mac or "Ctrl" if you are on Windows.
By clicking on the "Go" button, the right panel will be filled with the movies resulting from his search.

This application allows the user to have a list of favorite movies, if he clicks on "Favorites", all his favorite 
movies will appear in the right panel as well as the "Clear Favorites" button. This button grants the user the possibility 
to initialize his favorites, but for a security matter, a confirmation window will pop up right after clicking on
"Clear Favorites" to make sure the user truly wants to clear his favorites.

<img alt="Clear button display" src= "https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F8b95d872-c536-4651-ac3f-3dd74877b268%2FScreenshot_2024-03-01_at_21.08.57.png?table=block&id=a87927d9-0c11-41b7-a9a9-87d18b3fa187&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width="800">

Each film has a detail section that pops up in a window when clicking on the film's picture. If the movie isn't 
added to favorites, then the "Add To Favorites" button appears in the details section :

<img alt="Details section with add button" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F5c882cf5-3352-4f54-ad60-329ac0dcb6dc%2FScreenshot_2024-03-01_at_21.16.43.png?table=block&id=91b0b708-22e9-4613-816e-0db8e4361ca7&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width="800">

If the movie is already added to favorites, then the "Remove From Favorites" button will appear in the details section:

<img alt="Details section with remove button" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F807d8b64-a07a-410c-ab84-0de85b609206%2FScreenshot_2024-03-01_at_21.22.58.png?table=block&id=f9b14459-4222-4e65-ab0f-36aacbba4124&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width= "800">

At the end of the main page, the user can choose if he wants to go back to the "Previous Page" or to jump into the 
"Next Page" or to choose a specific page by entering a page number.

<img alt="Page display" src="https://fabien-marcuccini.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fb09fcc6c-df16-46f2-8a4f-d37dba6294b1%2F50822961-fd6b-47ed-a1fe-4425ca909850%2FUntitled.png?table=block&id=7efa86cb-b6e1-457f-8bc4-05f774ef4853&spaceId=b09fcc6c-df16-46f2-8a4f-d37dba6294b1&width=2000&userId=&cache=v2" width="800">

