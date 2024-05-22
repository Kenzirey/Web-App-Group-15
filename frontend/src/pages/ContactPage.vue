<!-- src/pages/ContactPage.vue -->

<template>
    <div>
        <section id="contactUsOverHead">
            <h1 class="space">Contact us!</h1>
            <p class="title-paragraph"> If there is anything you are wondering about
                ,or have any input on our website, we would love to hear from you!</p>
        </section>


        <section id="reportIssues">
            <section class="box">
                <h1 class="paragraphHeader">
                    Report issues
                </h1>
                <p class="pContact"> If there are any issues with our website, feel free to send a report to our
                    <a href="mailto:emmanes@stud.ntnu.no">email </a> or directly to those who worked on the website
                </p>
            </section>
        </section>

        <section id="adresses">
            <section class="box">
                <h1 class="paragraphHeader">
                    Contact us!
                </h1>
                <p class="pContact">  Address: Larsgårdsvegen 2, 6009 Ålesund <br>


                </p>

            </section>
        </section>

    </div>
</template>

<style scoped>


.box {
  padding: 75px 0;
  width: 800px;
  text-align: left;
  margin: 25px;
  display: inline-block;
}

.paragraphHeader {
  position: relative;
  top: -70px;
  left: 10px;
  font-size: 30px;
}

.pContact {
  position: relative;
  top: -70px;
  left: 10px;
  font-size: 20px;
}

.title-paragraph{
  font-size: 20px;
}

.space {
  padding: 15px 0;
}

</style>
