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