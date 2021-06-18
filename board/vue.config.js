const path = require('path')

module.exports ={
	outputDir: path.resolve(__dirname, "../"+"main/resources/static"),
	devServer: {
		// historyApiFallback: true,

		proxy: {
			'/':{
				target:'http://localhost:7070',
				ws:true,
				changeOrigin:true
			}
		}

	},
	// configureWebpack: {
	// 	resolve: {
	// 	  alias: {
	// 		assets: path.resolve("./src/assets"),
	// 	  },
	// 	},
	//   },
	// css: {
	// 	loaderOptions: {
	// 	  scss: {
	// 		additionalData: `
	// 					@import "@/assets/scss/_scss_header.scss";
	// 					@import "@/assets/scss/UIComplex.scss";
	// 					@import "@/assets/scss/Component.scss";
	// 				`,
	// 	  },
	// 	},
	//   },
}