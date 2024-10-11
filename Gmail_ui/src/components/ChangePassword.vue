<template>
    <div class="form-data">
        <div class="data">
            <h4 class="header">Change your password here</h4>
            <div class="container">
                <form @submit.prevent="submitForm">
                    <div class="form-group">
                        <label for="oldPassword">Old Password: </label>
                        <input type="password" id="oldPassword" v-model="values.oldPassword" required
                            @change="passwordCheck()" /><br>
                        <span v-if="passwordCheckResult === true" style="color:green;">PasswordVerified</span>
                        <span v-else-if="passwordCheckResult === false" style="color:red">Incorrect old password</span>
                        <div class="validation-error" v-if="values.oldPassword && !isValidPassword(values.oldPassword)">
                            Please enter a valid old password.
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password">New Password: </label>
                        <input type="password" id="newPassword" v-model="values.newPassword" required/>
                        <div class="validation-error" v-if="values.newPassword && !isValidPassword(values.newPassword)">
                            Password must be at least 6 characters.
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="confirmpassword">Confirm Password: </label>
                        <input type="password" id="confirmpassword" v-model="confirmPassword" required
                            @change="comparePasswords()" />
                    </div>
                    <div>
                        <button type="submit" class="btn">Submit</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

    
<script>
import axios from 'axios';

export default {
    name: "ChangePassword",
    data() {
        return {
            values: {
                oldPassword: null,
                newPassword: null,
            },
            confirmPassword: '',
            passwordCheckResult: null,
            verifyResponse: null,
        };
    },
    methods: {
        async passwordCheck() {
            try {
                console.log('old password:', this.values.oldPassword);
                const email = localStorage.getItem('email');
                const verifyResponse = await axios.post(
                    `http://localhost:8080/users/verifyPassword/${email}`,
                    {
                        oldPassword: this.values.oldPassword,
                    },
                    {
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );
                this.passwordCheckResult = true;
                console.log(verifyResponse);
            } catch (error) {
                this.passwordCheckResult = false;
                console.error('Invalid old password:', error);
            }
        },
        async submitForm() {
            if (this.isValidPassword(this.values.oldPassword) && this.isValidPassword(this.values.newPassword)) {
                try {
                    console.log("Form values", this.values);
                    const email = localStorage.getItem('email');
                    const response = await axios.put(
                        `http://localhost:8080/users/changePassword/${email}`,
                        this.values,
                        {
                            headers: {
                                "Content-Type": "application/json",
                            },
                        }
                    );
                    console.log("Password changed successful");
                    alert("Password changed successfully");
                    this.$router.push({ path: '/profile' });
                } catch (error) {
                    console.error("Password change failed", error);
                    this.errorMessage = "Password change failed";
                }
                // Clear the formValues for the next entry
                this.values = {
                    oldPassword: '',
                    newPassword: '',
                };
                this.confirmPassword = ''
            }
        },
        comparePasswords() {
            const password = this.values.newPassword;
            if (password === this.confirmPassword) {
                this.newPassword = password;
                this.passwordMatched = true;
                console.log('Passwords match');
            } else {
                console.error('Passwords do not match');
                alert('Password and confirm password must match');
                this.values.newPassword = '';
                this.confirmPassword = '';
            }
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

.form-group {
    margin-bottom: 20px;
}

.data {
    padding: 30px;
    border: 1px solid black;
}

.form-group {
    margin-bottom: 25px;
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