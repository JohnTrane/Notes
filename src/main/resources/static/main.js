var options = {
	"hostUrl": "http://192.168.86.37:8080/"
}

$(document).ready(main);

function  main() {
	reload();
}

async function reload() {
	clear();

	let notes = await getNotes();

	// notes.forEach(note => {
	// 	renderNote(note);
	// });

	notes.forEach(renderNote);
}

function closeAddNote() {
	$("#note-title").val("");
	$("#note-content").val("");
}

async function clickAddNote() {
	var title = $("#note-title").val();
	var content = $("#note-content").val();
	var password = $("#password-input").val();

	$("#note-title").val("");
	$("#note-content").val("");

	try {
		var result = await addNote(title, content, password);

		if (result.isSuccess) {
			reload();
		} else {
			alert("Didn't added");
		}
	} catch (e) {
		alert("Error");
	}
}

async function addNote(title, content, password) {
	var response = await fetch(`${options.hostUrl}/api/addNote`, {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json",
	        "Authorization": password
	    },
	    body: `{
	    	"title": "${title}",
	    	"content": "${content}"
	    }`
	});

	return await response.json();
}

async function clickDeleteNote(id){
	try {
		var result = await deleteNote(id);

		if (result.isSuccess) {
			reload();
		} else {
			alert("Didn't deleted");
		}
	} catch (e) {
		alert("Error");
	}
}

async function deleteNote(id) {
	var pwd = "password123";
	var response = await fetch(`${options.hostUrl}/api/deleteNote`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			"Authorization": pwd
		},
		body: `{
	    	"noteId": "${id}"
	    }`
	});

	return await response.json()
}


function renderNote(note) {
	var elem = $(`
		
			<button type="button"  class="btn btn-primary float-right" onclick="clickDeleteNote(${note.id})">delete</button>
		<h2>${note.title}</h2>
		<p class="lead">${note.content}</p>
		<br>
	`);

	$(".starter-template").append(elem);
}

function clear() {
	$(".starter-template").html("");
}

async function  getNotes() {
	var response = await fetch(`${options.hostUrl}/api/getNotes`, {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json"
	    },
	    body: `{

	    }`
	});

	return await response.json()
}
/*

<h1>Bootstrap starter template</h1>
<p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
<br>

*/