<template>
	<section>
		<h2>About Us</h2>
		<section id="members-container">
			<figure>
				<h3>Henrik</h3>
				<p>Full-stack developer</p>
				<v-img class="photo" :src="henrik" alt="Image of Henrik"></v-img>
			</figure>
			<figure>
				<h3>Jonas</h3>
				<p>User/Authentication</p>
				<v-img class="photo" :src="jonas" alt="Image of Jonas"></v-img>
			</figure>
			<figure>
				<h3>Emma</h3>
				<p>Manager/Full-stack</p>
				<v-img class="photo" :src="emma" alt="Image of Emma"></v-img>
			</figure>

			<figure>
				<h3>Tony</h3>
				<p>Sales forms & Wireframes</p>
				<v-img class="photo" :src="tony" alt="Image of Tony"></v-img>
			</figure>
			<figure>
				<h3>Eleanor</h3>
				<p>Assistant</p>
				<v-img class="photo" :src="emmaCat" alt="Image of Elanor"></v-img>
			</figure>
			<figure>
				<h3>Fazzi</h3>
				<p>Assistant</p>
				<v-img class="photo" :src="jonasCat" alt="Image of Fazzi"></v-img>
			</figure>

		</section>
	</section>
</template>

<script>
import jonas from '/images/AboutUs/J.png';
import tony from '/images/AboutUs/T.png';
import emma from '/images/AboutUs/emmaHonestReaction.png';
import henrik from '/images/AboutUs/HenrikIkkeSpør.jpg';
import emmaCat from '/images/AboutUs/EmmaKatt.jpg';
import jonasCat from '/images/AboutUs/JonasKatt.jpg';


export default {
	data() {
		return {
			jonas,
			tony,
			emma,
			henrik,
			emmaCat,
			jonasCat,
		}
	}
}

</script>

<style scoped>
.photo {
	min-width: 200px;
	min-height: 200px;
	aspect-ratio: 1;
	object-fit: cover;
}

#members-container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	max-width: 800px;
}

#members-container > * {
	margin: 10px;
}

p {
	margin-bottom: 8px;
}
figcaption {
	margin-top: 8px;
}
</style>
