<!-- src/pages/ContactPage.vue -->

<template>
    <div>
        <section id="contactUsOverHead">
            <h2 class="space">Contact us!</h2>
            <p> If there is anything you are wondering about
                ,or have any input on our website, we would love to hear from you!</p>
        </section>


        <section id="reportIssues" class="flex-container">
            <div class="box">
                <div class="topLeftText">
                    Report issues
                </div>
                <p class="pContact"> If there are any issues with our website, feel free to send a report to our
                    <a href="mailto:Tnhpham@stud.ntnu.no">email </a> or directly to those who worked on the website
                </p>
            </div>
        </section>

        <section id="adresses" class="flex-container">
            <div class="box">
                <div class="topLeftText">
                    Contact us :D
                </div>
                <p class="pContact">  Address: Larsgårdsvegen 2, 6009 Ålesund <br>


                </p>

                <!--TODO legg til kontakter diskuterer med gruppa -->
            </div>
        </section>

    </div>
</template>

<style lang="scss" scoped>

.flex-container {
  display: flex;
}

.box {
  padding: 75px 0;
  text-align: left;
  margin: 25px;
  display: inline-block;
  flex: 1; /* Equal height for both sections */
}

  .topLeftText {
    color: rgb(0, 0, 0);
    position: relative;
    top: -70px;
    left: 10px;
    font-size: 30px;
  }

  .pContact {
    color: rgb(0, 0, 0);
    position: relative;
    top: -70px;
    left: 10px;
    font-size: 20px;
  }

  .space {
  padding: 15px 0;
  }



</style>
