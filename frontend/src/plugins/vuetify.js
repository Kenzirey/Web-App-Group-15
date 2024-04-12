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
    primary: '#3F51B5',
    secondary: '#b4bbe4',
    background: '#e4e8f1',  //Background of main content area
    surface: '#e4e8f1',     //Background of the toolbar
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
