import { createVuetify } from 'vuetify'
import 'vuetify/styles'
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { VApp, VBtn, VTextField, VAppBar, 
  VTextarea, VRow, VCol, VContainer, VSpacer, VCarousel, VWindow } from 'vuetify/components'

/*
https://vuetifyjs.com/en/features/treeshaking/#automatic-treeshaking
*/

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
    VTextField,
    VAppBar,
    VTextarea,
    VRow,
    VCol,
    VContainer,
    VSpacer,
    VCarousel,
    VWindow

  }
})
