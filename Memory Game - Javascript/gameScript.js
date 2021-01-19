var memory_array = ["A", "\u0410", "B", "\u0411", "C", "\u0426", "D", "\u0414", "E", "\u0415", "F", "\u0424", "G", "\u0413", "I", "\u0418", "Z", "\u0417", "K", "\u041A"];
var memory_array_org = ["A", "\u0410", "B", "\u0411", "C", "\u0426", "D", "\u0414", "E", "\u0415", "F", "\u0424", "G", "\u0413", "I", "\u0418", "Z", "\u0417", "K", "\u041A"];
var memory_tiles = [];

var memory_tile_tmp = null;
var lock = false;
var score = 0;
var finish = memory_array.length/2;
var stimeStat;
var mtimeStat;
var scoreStat;
var name = "";
var playerCounter = 0;

function memory_tile_shuffle(array){
    var i = array.length, j, temp;
    while(--i > 0){
        j = Math.floor(Math.random() * (i+1));
        temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
	return array;
}

function newBoard(){
	var output = '';
   	memory_array=memory_tile_shuffle(memory_array);
	for(var i = 0; i < memory_array.length; i++){
		for(var j = 0; j < memory_array.length; j++){
			if(memory_array[i] == memory_array_org[j] && j % 2 == 0){
				output += '<div class="tile" id="' + j + '" onclick="flipTile(this)"><span>' + memory_array[i] + '</span></div>';
				break;
			}else if(memory_array[i] == memory_array_org[j] && j % 2 != 0){
				output += '<div class="tile" id="' + (j-1) + '" onclick="flipTile(this)"><span>' + memory_array[i] + '</span></div>';
				break;
			}
		}
	}
	document.getElementById('game_board').innerHTML = output;
	for (var i = 0; i < memory_array.length; i++) {
		console.log(memory_array[i] + " ");
	}
}

function flipTile(tile){
	if(lock)
		return;
	if(memory_tiles.length == 0){
		tile.style.transition="all 0.8s";
		tile.style.backgroundImage="url('black.jpg')";
		tile.firstChild.style.visibility="visible";
		memory_tile_tmp = tile;	
		memory_tiles.push(tile);
	}else if(memory_tiles.length == 1 && memory_tiles[0]!= tile){
		memory_tiles.push(tile);
		tile.style.transition="all 0.8s";
		tile.style.backgroundImage="url('black.jpg')";
		tile.firstChild.style.visibility="visible";
		lock = true;
		if(memory_tiles[0].id == memory_tiles[1].id){
			score++;
			finish--;
			memory_tile_tmp = null;
			lock = false;
			memory_tiles[0].style.backgroundImage="url('green.jpg')";
			memory_tiles[1].style.backgroundImage="url('green.jpg')";
			memory_tiles[0].firstChild.style.color="white";
			memory_tiles[1].firstChild.style.color="white";
			memory_tiles[0].onclick = "";
			memory_tiles[1].onclick = "";
			document.getElementById("Opened").innerHTML="SCORE: "+ score;
			memory_tiles = [];
		}else{
			setTimeout(function(){ 
				memory_tiles[0].firstChild.style.visibility="hidden";
				memory_tiles[0].style.backgroundImage="url('back.png')";
				memory_tiles[1].firstChild.style.visibility="hidden";
				memory_tiles[1].style.backgroundImage="url('back.png')";
				memory_tiles = [];
				score++;
				lock = false;
				document.getElementById("Opened").innerHTML="SCORE: "+ score;
			}, 
			1000);
		}
	}
	if(finish == 0) {
		window.alert("VICTORY");
   		clearTimeout(t);
   		document.getElementById("end").style.visibility="visible";
	}
}

//Stopwatch
var h2 = document.getElementById("time"), seconds = 0, minutes = 0, t;

function add() {
    seconds++;
    if (seconds >= 60) {
        seconds = 0;
        minutes++;
    }
    
    h2.innerHTML = "ELAPSED TIME: " + minutes + "mins " + seconds + "s";

    timer();
}
function timer() {
    t = setTimeout(add, 1000);
}

//buttons

function startGame(){
	if(name == null || name == ""){
		alert("Please eneter your name!");
	}else{
	document.getElementById("landingPage").style.display="none";
	document.getElementById("memoryPage").style.display="block";
	timer();
	newBoard();
	}
}

function endGame(){
	document.getElementById("landingPage").style.display="block";
	document.getElementById("memoryPage").style.display="none";
	newBoard();
	var playerName = document.createElement("p");
	var playerTime = document.createElement("p");
	var playerScore = document.createElement("p");
	var numberOfTiles = document.createElement("p");

	playerName.innerHTML = name;
	playerTime.innerHTML = minutes + "mins " + seconds + "s";
	playerScore.innerHTML = score;
	numberOfTiles.innerHTML = memory_array.length;

	if(playerCounter == 3){
		var rem = document.getElementById("Leaderboard");
		rem.removeChild(rem.childNodes[1]);
		rem.removeChild(rem.childNodes[1]);
		rem.removeChild(rem.childNodes[1]);
		rem.removeChild(rem.childNodes[1]);
		playerCounter--;
	}
	playerCounter++;

	playerName.classList.add("p2");
	playerTime.classList.add("p2");
	playerScore.classList.add("p2");
	numberOfTiles.classList.add("p3");

	document.getElementById("Leaderboard").appendChild(playerName);
	document.getElementById("Leaderboard").appendChild(playerTime);
	document.getElementById("Leaderboard").appendChild(playerScore);
	document.getElementById("Leaderboard").appendChild(numberOfTiles);

	seconds = 0;
	minutes = 0;
	h2.innerHTML = "ELAPSED TIME: " + minutes + "mins " + seconds + "s";
	score = 0;
	document.getElementById("Opened").innerHTML ="SCORE: 0";
	finish = memory_array.length/2;
	document.getElementById("end").style.visibility="hidden";
	document.getElementById("start").disabled = true;
	newBoard();
}

function othername() {
    name = document.getElementById("userInput").value;
    if(name == null || name == ""){
    	alert("Please enter your name!")
    }else{
    	document.getElementById("start").disabled = false;
    }
}

function AvoidSpace(event) {
    var k = event ? event.which : window.event.keyCode;
    if (k == 32) return false;
}