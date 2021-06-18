<template>
  <span :class="classObject" :style="{ width: this.inputWidth }">
    <input
      ref="input"
      :type="this.type"
      :value="value"
      :placeholder="this.placeholder"
      :required="this.required"
      :style="{ width: this.inputWidth }"
      @input="updateInput"
      @focusin="onInputFocusIn"
      @focusout="onInputFocusOut"
      :readonly="this.readonly"
      :disabled="this.disabled"
      @keyup.enter="onBtnKeyUp"
      :targetMethod="this.targetMethod"
    />
    <button
      ref="search"
      type="button"
      class="btn-search"
      v-if="this.type === 'search'"
      @click="onSearchBtnClick"
    >
      검색
    </button>
    <button
      ref="button"
      type="button"
      class="btn-clear"
      v-if="this.showClearBtn"
      @click="onClearBtnClick"
      @focusout="onInputFocusOut"
    >
      clear
    </button>
    <div v-if="!this.valid" class="valid-fail-text">{{ this.validText }}</div>
  </span>
</template>
<script>
export default {
  name: "InputBox",
  props: {
    type: {
      default: "text",
      validator: function (value) {
        return ["text", "password", "email", "search"].indexOf(value) !== -1;
      },
    },
    inputWidth: String,
    value: {
      default: "",
      require: true,
    },
    placeholder: {
      default: "",
    },
    valid: {
      default: true,
    },
    validText: {
      default: "",
    },
    required: {
      default: false,
    },
    readonly: {
      default: false,
    },
    disabled: {
      default: false,
    },
    targetMethod: {
      targetMethod: "",
    },
  },
  data() {
    return {
      isFocused: false,
    };
  },
  computed: {
    showClearBtn() {
      return this.isFocused && this.value !== "";
    },
    classObject() {
      return {
        "input-box": true,
        "show-btn": this.showClearBtn,
        "valid-fail": !this.valid,
        "type-search": this.type === "search",
      };
    },
  },
  methods: {
    updateInput(event) {
      // this.$emit('input', event.target.value);
      this.$emit("update:value", event.target.value);
    },
    onInputFocusIn() {
      this.isFocused = true;
    },
    onInputFocusOut(event) {
      let that;
      that = this;

      // document.activeElement의 값이 focusout 이벤트 이후에 설정되므로 setTimeout 처리
      setTimeout(function () {
        if (
          !(
            document.activeElement === that.$refs.input ||
            document.activeElement === that.$refs.button
          )
        ) {
          that.isFocused = false;
          that.$emit("focusout", event);
        }
      }, 1);
    },
    onClearBtnClick() {
      this.$emit("update:value", "");
    },
    onSearchBtnClick() {
      this.$emit("search", this.$refs.input.value);
    },
    onBtnKeyUp(event) {
      if (
        this.targetMethod != undefined &&
        this.targetMethod != "" &&
        event.key == "Enter"
      ) {
        this.targetMethod();
      }
    },
    focus() {
      this.$refs.input.focus();
    },
  },
};
</script>
