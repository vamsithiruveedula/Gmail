<template>
    <div class="form-data">
        <div class="data">
            <h1 class="header">Sign in</h1>
            <h4 class="header">Use your Gmail Account</h4>
            <div class="container">
                <form @submit.prevent="submitForm" class="signin-form">
                    <div class="form-group">
                        <label for="email" class="label">Email:</label>
                        <input class="form-input" type="email" id="email" v-model="values.email" required/>
                        <div class="validation-error" v-if="values.email && !isValidEmail(values.email)">
                            Please enter a valid email address.
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="label">Password:</label>
                        <input class="form-input" type="password" id="name" v-model="values.password" required/>
                        <div class="validation-error" v-if="values.password && !isValidPassword(values.password)">
                            Password must be at least 6 characters.
                        </div>
                    </div>
                    <div class="form-group">
                        <RouterLink to="/signup" class="link-button">Create Account</RouterLink>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="submit" class="btn"><i class="fas fa-sign-in"></i> Sign in</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                    <div style=" display: flex; justify-content: center;">
                        <RouterLink to="/forgotpassword" class="link-button">Forgot Password...?</RouterLink>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

    
<script>
import axios from 'axios';

export default {
    name: "SignIn",
    data() {
        return {
            values: {
                email: null,
                password: null,
            },
        };
    },
    methods: {
        async submitForm() {
            if (this.isValidEmail(this.values.email) && this.isValidPassword(this.values.password)) {
                try {
                    console.log("Form values", this.values);
                    const response = await axios.post(`http://localhost:8080/users/signin`, {
                        email: this.values.email,
                        password: this.values.password,
                    });
                    localStorage.setItem('email', this.values.email);
                    // Sign-in was successful, redirect or perform actions accordingly
                    console.log("Sign-in successful");
                    this.$router.push({ path: '/emailinbox' });
                } catch (error) {
                    // Sign-in failed, display an error message
                    console.error("Sign-in failed");
                    console.error("Error signing in", error);
                    this.errorMessage = "Sign-in failed";
                }
                // Clear the formValues for the next entry
                this.values = {
                    email: '',
                    password: '',
                };
            } else {
                alert("Enter valid details!...");
            }
        },
        isValidEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(email);
        },
        isValidPassword(password) {
            return password.length >= 6;
        },
    },
}
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

.form-group {
    margin-bottom: 25px;
}

.signin-form {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border-radius: 5px;
    background-color: #f9f9f9;
}
.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.label {
    font-weight: bold;
}

.header {
    text-align: center;
}

.btn {
    background-color: rgb(63, 172, 63);
    width: 30%;
    height: 100%;
    padding: 5px;
    border-radius: 20px;
}

.link-button {
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
    margin-right: 15px;
    /* Adjust margin as needed */
}

.validation-error {
    color: red;
}
</style>