import { createVuetify } from 'vuetify'
import 'vuetify/styles'  // Import Vuetify styles

// Import any Vuetify components you use here
import { VApp, VBtn, VTextField } from 'vuetify/components'

export default createVuetify({
  components: {
    VApp,
    VBtn,
    VTextField
  }
})