<template>
    <div class="form-data">
        <div class="data">
            <h4 class="header">Register Here</h4>
            <div class="container">
                <form @submit.prevent="submitForm">
                    <div class="form-group">
                        <label for="firstName" class="label">First Name: </label>
                        <input type="text" id="firstName" v-model="formValues.firstName" required />
                        <span v-if="isFirstNameValid === false && submitted" class="validation-error">First Name is required.</span>
                    </div>
                    <div class="form-group">
                        <label for="lastName" class="label">Last Name: </label>
                        <input type="text" id="lastName" v-model="formValues.lastName" required />
                        <span v-if="isLastNameValid === false && submitted" class="validation-error">Last Name is required.</span>
                    </div>
                    <div class="form-group">
                        <label>Gender: </label>
                        <label>
                            <input type="radio" v-model="formValues.gender" value="male" required/> Male
                        </label>
                        <label>
                            <input type="radio" v-model="formValues.gender" value="female" /> Female
                        </label>
                        <span v-if="!formValues.gender && submitted" class="validation-error">Please select a gender.</span>
                    </div>
                    <div class="form-group">
                        <label for="email" class="label">Email: </label>
                        <input type="email" id="email" v-model="formValues.email" required @change="emailCheck()" /><br />
                        <span v-if="isEmailValid === false && submitted" class="validation-error">Email is invalid.</span>
                        <span v-if="emailCheckResult === 'unavailable'" style="color:red;">It is taken, try another
                            one.</span>
                        <span v-else-if="emailCheckResult === 'available'" style="color:green">It is available</span>
                    </div>
                    <div class="form-group">
                        <label for="password" class="label">Password: </label>
                        <input type="password" id="password" v-model="formValues.password" required
                            :pattern="passwordPattern" />
                        <span v-if="isPasswordValid === false && submitted" class="validation-error">Password must meet the criteria.</span>
                    </div>
                    <div class="form-group">
                        <label for="confirmpassword" class="label">Confirm Password: </label>
                        <input type="password" id="confirmpassword" v-model="confirmPassword" required
                            :pattern="passwordPattern" @change="comparePasswords()" />
                    </div>
                    <div class="form-group">
                        <label for="mobileNo" class="label">Mobile No: </label>
                        <input type="number" id="mobileNo" v-model="formValues.mobileNo" required min="0" />
                        <span v-if="isMobileNoValid === false && submitted" class="validation-error">Mobile Number is required and must be a
                            positive number.</span>
                    </div>
                    <div>
                        <button @click.prevent="goBack()" class="btn-clr">Go Back</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="reset" class="btn-clr">Clear</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="submit" class="btn">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';

export default {
    name: "SignUp",
    data() {
        return {
            formValues: {
                email: '',
                firstName: '',
                lastName: '',
                password: '',
                mobileNo: '',
            },
            confirmPassword: '',
            passwordMatched: false,
            emailCheckResult: '',
            passwordPattern: '^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$', // Example: At least 8 characters, one lowercase, one uppercase, one digit
            isFirstNameValid: false,
            isLastNameValid: false,
            isEmailValid: false,
            isPasswordValid: false,
            isMobileNoValid: false,
        };
    },
    methods: {
        async emailCheck() {
            try {
                const response = await axios.post('http://localhost:8080/users/emailCheck', {
                    email: this.formValues.email,
                });
                console.log(email);
                this.emailCheckResult = 'available';
            } catch (error) {
                console.log('Error checking email:', error);
                this.emailCheckResult = 'unavailable';
            }
        },
        comparePasswords() {
            const password = this.formValues.password;
            if (password === this.confirmPassword) {
                this.password = password;
                this.passwordMatched = true;
                console.log('Passwords match');
            } else {
                console.error('Passwords do not match');
                alert('Password and confirm password must match');
                this.formValues.password = '';
                this.confirmPassword = '';
            }
        },
        async submitForm() {
            try {
                console.log("Form values", this.formValues);
                const response = await axios.post('http://localhost:8080/users/signup', this.formValues);
                console.log("Registration successful");
                alert("Registration successful");
                this.$router.push('/signin');
            } catch (error) {
                console.error("Registration failed");
                alert("Registration failed");
                this.errorMessage = "Registration failed";
            } finally {
                // Clear the formValues for the next entry
                this.formValues = {
                    email: '',
                    firstName: '',
                    lastName: '',
                    password: '',
                    mobileNo: '',
                };
                this.confirmPassword = '';
                this.passwordMatched = false;
                this.emailCheckResult = '';
            }

        },
        goBack() {
            this.$router.push('/signin')
        },
    },
    isFormValid() {
        // Check individual field validity and return true if all fields are valid
        return (
            this.isFirstNameValid &&
            this.isLastNameValid &&
            this.isEmailValid &&
            this.isPasswordValid &&
            this.isMobileNoValid
        );
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

.btn {
    background-color: rgb(63, 172, 63);
    width: 30%;
    height: 100%;
    padding: 5px;
    border-radius: 40px;
}

.form-group {
    margin-bottom: 20px;
}

label {
    font-weight: bold;
}

.header {
    text-align: center;
}

.btn-clr {
    width: 25%;
    height: 100%;
    padding: 5px;
}

.validation-error {
    color: red;
}
</style>
  
<!--<template>
    <div class="form-data">
        <div class="data">
            <h4 class="header">Register Here</h4>
            <div class="container">
                <form @submit.prevent="submitForm">
                    <div class="form-group">
                        <label for="firstName">First Name: </label>
                        <input type="text" id="firstName" v-model="formValues.firstName"
                            @input="fieldTouched('firstName')" />
                        <div class="validation-error" v-if="shouldShowError('firstName')">
                            Please enter a valid first name (letters and spaces only).
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name: </label>
                        <input type="text" id="lastName" v-model="formValues.lastName" @input="fieldTouched('lastName')" />
                        <div class="validation-error" v-if="shouldShowError('lastName')">
                            Please enter a valid last name (letters and spaces only).
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email">Email: </label>
                        <input type="email" id="email" v-model="formValues.email" @change="emailCheck"
                            @input="fieldTouched('email')" /><br>
                        <div class="validation-error" v-if="shouldShowError('email')">
                            Please enter a valid email address.
                        </div>
                        <span v-if="emailCheckResult === 'unavailable'" style="color:red;">It is taken, try for another
                            one.</span>
                        <span v-else-if="emailCheckResult === 'available'" style="color:green">It is available</span>
                    </div>
                    <div class="form-group">
                        <label for="password">Password: </label>
                        <input type="password" id="password" v-model="formValues.password"
                            @input="fieldTouched('password')" />
                        <div class="validation-error" v-if="shouldShowError('password')">
                            Password must be at least 6 characters long.
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="confirmpassword">Confirm Password: </label>
                        <input type="password" id="confirmpassword" v-model="confirmPassword" @change="comparePasswords"
                            @input="fieldTouched('confirmPassword')" />
                        <div class="validation-error" v-if="!passwordMatched && confirmPassword">
                            Password and confirm password must match.
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobileNo">Mobile No: </label>
                        <input type="number" id="mobileNo" v-model="formValues.mobileNo"
                            @input="fieldTouched('mobileNo')" />
                        <div class="validation-error" v-if="shouldShowError('mobileNo')">
                            Please enter a valid 10-digit mobile number.
                        </div>
                    </div>
                    <div>
                        <button @click.prevent="goBack()" class="btn-clr">Go Back</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="reset" class="btn-clr">Clear</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="submit" class="btn">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "SignUp",
    data() {
        return {
            formValues: {
                email: '',
                firstName: '',
                lastName: '',
                password: '',
                mobileNo: '',
            },
            confirmPassword: '',
            passwordMatched: false,
            emailCheckResult: '',
            validationRules: {
                firstName: /^[A-Za-z\s]+$/,
                lastName: /^[A-Za-z\s]+$/,
                email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                password: /^.{6,}$/,
                mobileNo: /^\d{10}$/,
            },
            touchedFields: {
                firstName: false,
                lastName: false,
                email: false,
                password: false,
                confirmPassword: false,
                mobileNo: false,
            },
        };
    },
    methods: {
        fieldTouched(fieldName) {
            this.touchedFields[fieldName] = true;
        },
        shouldShowError(field) {
            const value = this.formValues[field];
            const rule = this.validationRules[field];
            if (this.touchedFields[field]) {
                if (!value || (rule && !rule.test(value))) {
                    return true;
                }
            }
            return false;
        },
        async emailCheck() {
            try {
                const response = await axios.post('http://localhost:8080/users/emailCheck', {
                    email: this.formValues.email,
                },);
                this.emailCheckResult = 'available';
            } catch (error) {
                console.log('Error checking email:', error);
                this.emailCheckResult = 'unavailable';
            }
        },
        comparePasswords() {
            const password = this.formValues.password;
            if (password === this.confirmPassword) {
                this.password = password;
                this.passwordMatched = true;
                console.log('Passwords match');
            } else {
                console.error('Passwords do not match');
                alert('Password and confirm password must match');
                this.formValues.password = '';
                this.confirmPassword = '';
            }
        },
        async submitForm() {
            try {
                // Check for validation errors
                for (const field of Object.keys(this.formValues)) {
                    if (this.shouldShowError(field)) {
                        console.error(`Validation failed for ${field}`);
                        return;
                    }
                }
                console.log("Form values", this.formValues);
                const response = await axios.post('http://localhost:8080/users/signup', this.formValues);
                console.log("Registration successful");
                alert("Registration successful");
                this.$router.push('/signin');
            } catch (error) {
                console.error("Registration failed");
                alert("Registration failed");
                this.errorMessage = "Registration failed";
            } finally {
                // Clear the formValues for the next entry
                this.formValues = {
                    email: '',
                    firstName: '',
                    lastName: '',
                    password: '',
                    mobileNo: '',
                };
                this.confirmPassword = '';
                this.passwordMatched = false,
                this.emailCheckResult = ''
            }
        },
        goBack() {
            this.$router.push('/signin')
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

.btn {
    background-color: rgb(76, 189, 218);
    width: 30%;
    height: 100%;
    padding: 5px;
    border-radius: 40px;
}

.form-group {
    margin-bottom: 20px;
}

label {
    font-weight: bold;
}

.header {
    text-align: center;
}

.btn-clr {
    width: 25%;
    height: 100%;
    padding: 5px;
}

.validation-error {
    color: red;
}</style> -->