<template>
	<div :class="classObject" v-if="visible" @click.self="handleWrapperClick">
		<div class="modal-popup-inner" :style="{'width': width}">
			<div class="modal-popup-header">
				<span class="modal-popup-title">{{ title }}</span>
				<button type="button" class="btn-modal-close" @click="$emit('update:visible', !visible)">레이어 팝업 닫기</button>
			</div>
			<div class="modal-popup-body">
				<div class="modal-popup-body-inner">
					<slot></slot>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
export default {
	name: 'ModalPopup',
	props: {
		type: {
			type: String,
			default: '',
			validator: function (value) {
				return ['', 'type-black'].indexOf(value) !== -1
			},
		},
		width: {
			type: String,
		},
		title: {
			type: String,
			require: true,
			default: '',
		},
		visible: {
			type: Boolean,
			require: true,
			default: false,
		},
		dimClickClose: {
			type: Boolean,
			default: false,
		}
	},
	computed: {
		classObject() {
			return {
				'modal-popup': true,
				'type-black': this.type === 'type-black',
			}
		},
		
	},
	methods: {
		handleWrapperClick(){
			if (this.dimClickClose) {
				this.$emit('update:visible', false);
			}
		},
	},
}
</script>