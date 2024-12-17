
// JavaScript Code
const studentForm = document.getElementById('student_form');
const studentList = document.getElementById('students');
const resetButton = document.getElementById('reset-list');

let students = [];

// Function to render the student list
function renderStudentList() {
    studentList.innerHTML = '';

    // Add each student to the list
    students.forEach((student, index) => {
        const li = document.createElement('li');
        li.innerHTML = `
            ${student.name} &emsp; 
            Email: ${student.email} &emsp;
            Phone: ${student.phone} &emsp;
            DOB: ${student.birthday} &emsp;
            Program: ${student.program} &emsp;
            Year of Study: ${student.yearOfStudy} &emsp;
            Skills: ${student.skills.join(', ')}
            <hr>
        `;
        
        // Create a delete button for each student
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', () => deleteStudent(index));
        
        li.appendChild(deleteButton);
        studentList.appendChild(li);
    });
    
    // Clear list
// Add student per student in array
// add delete student event to each student
}

// Function to add a new student
function addStudent(event) {
// prevent form submission
// add student to array
// reset the form
// render student list
event.preventDefault(); // Prevent form submission

// Get student details from the form
const name = document.getElementById('full-name').value;
const email = document.getElementById('email').value;
const phone = document.getElementById('phone').value;
const birthday = document.getElementById('dob').value;
const program = document.getElementById('topic').value;

const yearOfStudy = document.querySelector('input[name="yos"]:checked')?.value || 'N/A';
const skills = Array.from(document.querySelectorAll('input[type="checkbox"]:checked')).map(
    (checkbox) => checkbox.value
);


// Add the student to the array
const student = { name, email, phone, birthday, program, yearOfStudy, skills };
students.push(student);

// Reset the form
studentForm.reset();

// Render the updated student list
renderStudentList();
}

// Function to delete a student
function deleteStudent(index) {
// splice the index of the array to remove the student
// re render the student list
students.splice(index,1)
renderStudentList();
}

// Function to reset the student list
function resetStudentList() {
    students = [];
    renderStudentList();
// clear the array
// re render the student list
}

// Add event listeners
studentForm.addEventListener('submit', addStudent);
resetButton.addEventListener('click', resetStudentList);