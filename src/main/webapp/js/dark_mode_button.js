window.addEventListener("load", function() {
    let isDarkModeOn = localStorage.getItem("isDarkModeOn");

    isDarkModeOn = isDarkModeOn != null && isDarkModeOn == "true";

    if(isDarkModeOn) {
        document.documentElement.setAttribute('data-bs-theme', "dark");
        document.getElementById('dark-mode-button').checked = true;
    } else {
        document.documentElement.setAttribute('data-bs-theme', "light");
        document.getElementById('dark-mode-button').checked = false;
    }
});

function switchTheme() {
    let darkModeButton = document.getElementById('dark-mode-button');
    if(darkModeButton == null) {
        return;
    } else if (darkModeButton.checked) {
        document.documentElement.setAttribute('data-bs-theme','dark');
        localStorage.setItem("isDarkModeOn", "true");
    } else {
        document.documentElement.setAttribute('data-bs-theme', 'light');
        localStorage.setItem("isDarkModeOn", "false");
    }
}