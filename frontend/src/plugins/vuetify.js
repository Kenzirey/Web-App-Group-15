/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Composables
import { createVuetify } from 'vuetify'

const lightTheme = {
  dark: false,
  colors: {
    /*Need to add colors to features course background etc */
    primary: '#3f2b96',
    secondary: '#b4bbe4',
    background: '#d6e1ff',  //Background of main content area
    gradiantOne:'#336dff', //left color
    gradiantTwo:'#3f2b96', //Right color
    accent: '#2196f3',
    error: '#f44336',
    warning: '#ff9800',
    info: '#03a9f4',
    success: '#8bc34'
  },
  variables: {
    'border-color': '#000000',
  }
}

//constant of custom theme goes here, 

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: 'lightTheme',
    themes: {
      lightTheme,
    },
  },
})
