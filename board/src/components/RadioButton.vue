<template>
	<span class="ui-rdo">
		<input :id="inputId" type="radio" :checked="checked" :disabled="disabled" :name="name" :value="value" @change="onRadioChange"><label :for="inputId"><span>{{ text }}</span></label>
	</span>
</template>
<script>
export default {
	props: {
		text: {
			default: ''
		},
		value: {
			default: ''
		},
		name: {
			default: ''
		},
		checked: {
			default: false,
		},
		disabled: {
			default: false,
		},
	},
	data() {
		return {
			uid: null,
			inputId: null,
		}
	},
	created() {
		this.uid = this._uid;
		this.inputId = 'rdo' + this.uid;
	},
	methods: {
		onRadioChange(event) {
			this.$emit('radioChange', event);
		}
	}
}
</script>