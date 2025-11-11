const notesTableContainer = document.getElementById("notes-table-container")
const noteModal = document.getElementById('noteModal');
const addNoteBtn = document.getElementById('addNoteBtn'); // Assuming a button to open the modal
const closeButton = document.querySelector('.close-button');
const noteForm = document.getElementById('noteForm');
const noteTitleInput = document.getElementById('noteTitle');
const noteDescriptionInput = document.getElementById('noteDescription');
const saveNoteBtn = document.getElementById('saveNoteBtn');
const cancelNoteBtn = document.getElementById('cancelNoteBtn');
let currentNoteId = null;

function openNoteModal(note = null) {
  noteModal.style.display = 'flex';
  noteForm.removeEventListener('submit', addNoteHandler);
  noteForm.removeEventListener('submit', editNoteHandler);

  if (note) {
      // For editing
      currentNoteId = note.id;
      noteTitleInput.value = note.title;
      noteDescriptionInput.value = note.description;
      saveNoteBtn.textContent = 'Save Changes';
      noteForm.addEventListener('submit', editNoteHandler);
    } else {
      // For adding a new note
      currentNoteId = null;
      noteTitleInput.value = '';
      noteDescriptionInput.value = '';
      saveNoteBtn.textContent = 'Save Note';
      noteForm.addEventListener('submit', addNoteHandler);
    }
}

function closeNoteModal() {
    noteModal.style.display = 'none';
}

addNoteBtn.addEventListener('click', () => openNoteModal());
closeButton.addEventListener('click', closeNoteModal);
cancelNoteBtn.addEventListener('click', closeNoteModal);

window.addEventListener('click', (event) => {
  if (event.target === noteModal) {
    closeNoteModal();
  }
});

const addNoteHandler = (event) => {
    event.preventDefault();
    addNote();
};

const editNoteHandler = (event) => {
    event.preventDefault();
    if (currentNoteId !== null) {
        editNote(currentNoteId);
    }
};

async function addNote() {
    const title = noteTitleInput.value.trim();
    const description = noteDescriptionInput.value.trim();
    if (title && description) {
        // console.log('New Note:', { title, description });
        await fetch("/notes", {
                method: "POST",
                headers: {"Content-Type" : "application/json"},
                body: JSON.stringify({title, description})
            });
        alert('Note saved!');
        closeNoteModal();
      } else {
        alert('Please enter both a title and content for your note.');
      }
    await loadNotes();
}

async function editNote(id) {
    const title = noteTitleInput.value.trim();
    const description = noteDescriptionInput.value.trim();
    if (title && description) {
        await fetch(`/notes/${id}`, {
                method: "PATCH",
                headers: {"Content-Type" : "application/json"},
                body: JSON.stringify({title, description})
            });
        alert('Note updated successfully!');
        closeNoteModal();
    } else {
        alert('Please enter both a title and description for your note.');
    }
    await loadNotes();
}

async function deleteNote(id) {
    if (confirm('Are you sure you want to delete this note?')) {
        await fetch(`/notes/${id}`, {
            method: "DELETE"
        });
        alert('Note deleted!');
        await loadNotes();
    }
}

function buildTable(data, container) {
  container.innerHTML = '';
  if (data.length === 0) {
      container.textContent = 'No notes found.';
      return;
  }
  const table = document.createElement("table");
  const thead = document.createElement("thead");
  const headerRow = document.createElement("tr");

  const headers = ['Title', 'Description', 'Actions'];

  headers.forEach(headerText => {
    const th = document.createElement("th");
    th.textContent = headerText;
    headerRow.appendChild(th);
  });

  thead.appendChild(headerRow);
  table.appendChild(thead);

  const tbody = document.createElement("tbody");
  data.forEach((rowData) => {
    const row = document.createElement("tr");

    const titleTd = document.createElement("td");
    titleTd.textContent = rowData.title;
    row.appendChild(titleTd);

    const descriptionTd = document.createElement("td");
    descriptionTd.textContent = rowData.description;
    row.appendChild(descriptionTd);

    const actionsTd = document.createElement("td");

    const editBtn = document.createElement("button");
    editBtn.textContent = 'Edit';
    editBtn.classList.add('edit-btn');

    const noteData = {
        id: rowData.id,
        title: rowData.title,
        description: rowData.description
    };
    editBtn.addEventListener('click', () => openNoteModal(noteData));
    actionsTd.appendChild(editBtn);

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = 'Delete';
    deleteBtn.classList.add('delete-btn');
    deleteBtn.addEventListener('click', () => deleteNote(rowData.id));
    actionsTd.appendChild(deleteBtn);

    row.appendChild(actionsTd);

    tbody.appendChild(row);
  });
  table.appendChild(tbody);

  container.appendChild(table);
}

async function loadNotes() {
        const res = await fetch("/notes");
        const data = await res.json();
        buildTable(data, notesTableContainer);
    }
loadNotes();