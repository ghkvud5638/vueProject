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

	}
}