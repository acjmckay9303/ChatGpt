function sendMessage() {
    var form = document.getElementById("messageForm");
    var formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            document.querySelector('.chat-messages').innerHTML = data;
            document.getElementById('inputMessage').value = '';
        })
        .catch(error => {
            console.error(error);
        });
}
