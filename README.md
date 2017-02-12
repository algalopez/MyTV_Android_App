# MyTV_Android_App

<p>
Search and save relevant information about your favourite tv shows and movies.
</p>
<p>
<b>Author: </b>Alvaro Garcia Lopez (algalopez)
</p>

<p>
<b>Notes: </b> The OMDB API search function is not available any more, so this project is discontinued.
</p>


<h2>Screenshots:</h2>
<p align="center">
<img src="https://github.com/algalopez/MyTV_Android_App/blob/master/Favourites.png" width="240">
</p>


<h2>Structure:</h2>
<ul>
    <li><b>presentation</b></li>
    <ul>
        <li><b>activity: </b></li>
        <li><b>adapter: </b>ListView Base adapters</li>
        <li><b>fragment: </b></li>
        <li><b>presenter: </b>Middleman between views and domain</li>
    </ul>
    <li><b>Domain</b></li>
    <ul>
        <li><b>interactor: </b>Use cases</li>
        <li><b>model: </b>Plain old Java Objects</li>
        <li><b>presentation: </b></li>
        <li><b>repository: </b>Interfaces to the data layer</li>
        <li><b>scheduler: </b>Loaders, executors, etc.</li>
    </ul>
    <li><b>Data</b></li>
    <ul>
        <li><b>history: </b>Local storage for previous searches</li>
        <li><b>local: </b>Local storage for movies and tv shows</li>
        <li><b>omdb: </b>OMDB network repository</li>
    </ul>
</ul>
