<template>
    <div class="form-data">
        <div class="data">
            <h2 class="header">Reset Password</h2>
            <form @submit.prevent="resetPassword" class="reset-password-form">
                <div class="form-group">
                    <label for="newPassword"><b>New Password:</b></label>
                    <input type="password" id="newPassword" v-model="newPassword" class="form-input" required/>
                </div>
                <div class="form-group">
                    <label for="confirmPassword"><b>Confirm Password:</b></label>
                    <input type="password" id="confirmPassword" v-model="confirmPassword" class="form-input" required/>
                </div>
                <button type="submit" class="btn">Reset Password</button>
            </form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            newPassword: '',
            confirmPassword: '',
        };
    },
    methods: {
        isValidPassword(password) {
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            return passwordRegex.test(password);
        },
        
        async resetPassword() {
            // Add logic to reset the user's password
            if (this.newPassword === this.confirmPassword) {
                try {
                    const email = localStorage.getItem('email');
                    const response = await axios.put(
                        `http://localhost:8080/users/resetPassword/${email}`, {
                        newPassword: this.newPassword,
                    });
                    localStorage.removeItem(email);
                    alert('Password reset successfully');
                    this.$router.push('/signin');
                } catch (error) {
                    console.error('Failed to reset password:', error);
                }
            } else {
                alert('New password and confirm password must match');
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

.reset-password-form {
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
</style>
  