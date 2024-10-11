<template>
  <div>
    <h2 class="header">Compose Email</h2>
    <div class="compose-form">
      <form @submit.prevent="sendEmail">
        <div class="form-group">
          <label for="recipient">To:</label>
          <input type="text" id="recipient" v-model="recipientIds" placeholder="Enter recipient email(s)" />
        </div>
        <div class="form-group">
          <label for="subject">Subject:</label>
          <input type="text" id="subject" v-model="subject" placeholder="Enter email subject" />
        </div>
        <div class="form-group">
          <label for="message">Message:</label>
          <textarea id="message" v-model="body" placeholder="Enter your message"></textarea>
        </div>
        <div class="form-group">
          <button type="submit">Send</button>
        </div>
      </form>
    </div>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  data() {
    return {
      recipientIds: '',
      subject: '',
      body: '',
    };
  },
  methods: {
    sendEmail() {
      const email = localStorage.getItem('email');
      // Convert the comma-separated string of recipients to an array
      const recipientArray = this.recipientIds.split(',').map(email => email.trim());

      axios.post(`http://localhost:8080/users/${email}/send`, {
        recipientIds: recipientArray, // Use the updated field name
        subject: this.subject,
        body: this.body, // Use the updated field name
      })
        .then(response => {
          console.log('Email sent successfully', response);
          this.$router.push('/emailsent');
        })
        .catch(error => {
          console.error('Error sending email', error);
        });
      // Reset form fields  
      this.recipientIds = '';
      this.subject = '';
      this.body = '';
    },
  },
};
</script>

<style scoped>
.header {
  margin-top: 0;
  margin-bottom: 25px;
}

/* .compose-form {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
} */

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  display: block;
}

input[type="text"],
textarea {
  width: 30%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

textarea {
  resize: vertical;
  min-height: 150px;
}

button[type="submit"] {
  background-color: #007BFF;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button[type="submit"]:hover {
  background-color: #0056b3;
}
</style>
  