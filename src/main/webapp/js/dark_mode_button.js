window.addEventListener("load", function() {
    if(document.documentElement.getAttribute('data-bs-theme') == "dark") {
        document.getElementById('dark-mode-button').checked = true;
    } else {
        document.getElementById('dark-mode-button').checked = false;
    }
});

function switchTheme() {
    let darkModeButton = document.getElementById('dark-mode-button');
    if(darkModeButton == null) {
        return;
    } else if (darkModeButton.checked) {
        document.documentElement.setAttribute('data-bs-theme','dark');
    } else {
        document.documentElement.setAttribute('data-bs-theme', 'light');
    }
}