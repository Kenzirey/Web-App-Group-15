import { createVuetify } from 'vuetify'
import 'vuetify/styles'
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { VApp, VBtn, VTextField } from 'vuetify/components'



export default createVuetify({
  icons: {
    iconfont: 'mdi', 
  },
  theme: {
    defaultTheme: 'light'
  },
  components: {
    components,
    directives,
    VApp,
    VBtn,
    VTextField
  }
})
