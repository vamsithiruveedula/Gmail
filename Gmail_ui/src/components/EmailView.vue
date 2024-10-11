<template>
  <div class="email-details-container">
    <h2>Email Details</h2>
    <div v-if="email" class="email-details">
      <div class="email-header">
        <strong>From: {{ email.senderId }}</strong>
      </div>
      <div v-if="email.recipientIds" class="email-header">
        <strong>To: {{ email.recipientIds.join(', ') }}</strong>
      </div>
      <div class="email-header">
        <strong>Subject:</strong> {{ email.subject }}
      </div>
      <div class="email-header">
        <strong>Date:</strong> {{ formatDate(email.sentDate) }}
      </div>
      <div class="email-body">
        <strong>Message:</strong>
        <p>{{ email.body }}</p>
      </div>
      <div class="email-footer">
        <button @click="goBack">Go Back</button>
      </div>
    </div>
    <div v-else>
      <p class="not-found-message">Email not found.</p>
    </div>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: {}
    }
  },

  methods: {
    fetchEmails() {
      const id = this.$route.params.id;
      axios.get(`http://localhost:8080/users/getEmail/${id}`)
        .then((response) => {
          this.email = response.data
          console.log('Email details:', this.email);
        })
        .catch((error) => {
          console.error('Error fetching email:', error);
        });
    },
    formatDate(dateStr) {
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric', hour12: true };
      return new Date(dateStr).toLocaleString(undefined, options);
    },
    goBack() {
      this.$router.push('/emailinbox');
    },
  },
  mounted() {
    this.fetchEmails();
  },
};
</script>
<style scoped>
.email-details-container {
  max-width: 500px;
  /* margin: 0 auto; */
  padding: 20px;
}

h2 {
  font-size: 24px;
  margin: 0;
  padding-bottom: 10px;
  /* border-bottom: 2px solid #007bff; */
}

.email-header {
  margin-top: 15px;
}

.email-body {
  margin-top: 20px;
}

.email-footer {
  margin-top: 20px;
  text-align: center;
}

.back-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: #0056b3;
}

.not-found-message {
  font-size: 18px;
  text-align: center;
  color: #ff0000;
  margin-top: 20px;
}
/* .email-details-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  background-color: #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

h2 {
  font-size: 24px;
  margin-top: 0;
}

.email-header {
  margin-top: 10px;
  font-weight: bold;
}

.email-body {
  margin-top: 20px;
}

.email-footer {
  margin-top: 20px;
  text-align: center;
}

.back-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: #0056b3;
}

.not-found-message {
  font-size: 18px;
  text-align: center;
  color: #ff0000;
  margin-top: 20px;
} */
</style>
  