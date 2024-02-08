function submitSearch() {
    const query = new URLSearchParams();
    query.append("q", document.getElementById("search-bar").value)
    if (query.get("q")) {
        location.href = "./search?" + query.toString();
    }
    return false;
}

document.getElementById("search-bar").addEventListener("focus", function() {
    document.getElementById("search-suggestions").style.display = "grid";
});

document.addEventListener("click", function(event) {
    const suggestionsContainer = document.getElementById("suggestions-container");
    if (!suggestionsContainer.contains(event.target)) {
        document.getElementById("search-suggestions").style.display = "none";
    }
});