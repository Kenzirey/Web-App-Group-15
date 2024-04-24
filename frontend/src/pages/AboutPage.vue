<template>
        <section>
            <h2>About Us</h2>
            <p>We're pretty cool, just so you know B)</p>
            <h2>Meet the team</h2>
            <div id="members-container">
                <div>
                    <h3>Member 1</h3>
                    <div class="photo">Placeholder photo</div>
                </div>
                <div>
                    <h3>Member 2</h3>
                    <div class="photo">Placeholder photo</div>
                </div>
                <div>
                    <h3>Member 3</h3>
                    <div class="photo">Placeholder photo</div>
                </div>
                <div>
                    <h3>Member 4</h3>
                    <div class="photo">Placeholder photo</div>
                </div>
                <div>
                    <h3>Member 5</h3>
                    <div class="photo">Placeholder photo</div>
                </div>
            </div>
        </section>
</template>

<style scoped>
.photo {
    background-color: #ff0000;
    min-width: 200px;
    min-height: 200px;
}

#members-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

#members-container > * {
    margin: 10px;
}
</style>