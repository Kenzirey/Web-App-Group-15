<template>
  <div class="splashscreen">

    <img src="/images/loadingCircleThing.gif" alt="Loading..." class="loading-image">
    <p class=" text">Loading</p>

  </div>
</template>

<script>
export default {
  name: "SplashScreen",
};
</script>

<style>
 .splashscreen {
   height: 60vh;
   display: flex;
   justify-content: center;
   flex-direction: column;
 }

 .loading-image {
   width: 100px; /* Set the width to your desired size */
   height: auto; /* Maintain aspect ratio */
 }

 .text {
   font-size: x-large;
 }
</style>
