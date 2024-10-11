<template>
    <div class="form-data">
        <div class="data">
            <h2 class="header">Forgot Password</h2>
            <form @submit.prevent="submitForm" class="forgot-password-form">
                <div class="form-group">
                    <label for="email"><b>Email:</b></label>
                    <input type="email" id="email" v-model="email" class="form-input" required/>
                </div>
                <div class="form-group">
                    <label for="mobile"><b>Mobile Number:</b></label>
                    <input type="text" id="mobile" v-model="mobileNo" class="form-input" required/>
                </div>
                <button type="submit" class="btn">Submit</button>
            </form>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';

export default {
    data() {
        return {
            email: '',
            mobileNo: '',
        };
    },
    methods: {
        async submitForm() {
            try {
                const response = await axios.post(`http://localhost:8080/users/forgotPassword`, {
                    email: this.email,
                    mobileNo: this.mobileNo,
                });
                localStorage.setItem('email', this.email);
                this.$router.push('/resetpassword');
            } catch (error) {
                console.error('Error:', error);
                alert('Please enter valid email and mobile number');
            }
        },
    },
};
</script>
  
<style scoped>
.form-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
}

.data {
  padding: 30px;
  border: 1px solid black;
}

.header {
  text-align: center;
}

.forgot-password-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 20px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.btn {
  background-color: rgb(63, 172, 63);
  width: 30%;
  height: 100%;
  padding: 5px;
  border-radius: 20px;
  color: #fff;
  border: none;
  cursor: pointer;
}

.btn:hover {
  background-color: #3fad3f;
}

.validation-error {
  color: red;
}
</style>
  