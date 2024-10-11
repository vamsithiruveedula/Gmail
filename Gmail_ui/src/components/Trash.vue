<template>
    <div class="trash">
        <h2 class="header">Trash Emails</h2>
        <div v-if="emails.length === 0">
            <p>No emails in your trash.</p>
        </div>
        <div v-else>
            <button @click="deleteSelectedEmails"><i class="fa fa-trash" aria-hidden="true"></i></button>
            <ul class="email-list">
                <li v-for="email in emails" :key="email.id" class="main-item">
                    <div class="email-item">
                        <div class="email-details">
                            <div class="email-header">
                                <div class="part_1">
                                    <div class="checkbox">
                                        <input type="checkbox" v-model="email.selected" />&nbsp;&nbsp;&nbsp;&nbsp;
                                        <!-- <button @click="toggleStarred(email)">
                                            <i v-if="email.starred" class="far fa-star" :class="starClass(email)"></i>
                                            <i v-else class="fa fa-star checked"></i>
                                        </button>&nbsp;&nbsp;&nbsp;&nbsp; -->
                                    </div>
                                    <div class="email-sender">From: {{ email.senderId }}</div>
                                </div>
                                <div class="email-date">Date: {{ formatDate(email.sentDate) }}</div>
                                <div class="part_2">
                                    <div class="eye" @click="emailView(email)">
                                        <i class="fa fa-eye" style="font-size:15px"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
  

<script>
import axios from 'axios';

export default {
    data() {
        return {
            emails: [],
        }
    },
    methods: {
        fetchEmails() {
            const email = localStorage.getItem('email');
            axios.get(`http://localhost:8080/users/get/${email}/trash`)
                .then((response) => {
                    console.log('Fetched emails:', response.data);
                    this.emails = response.data;
                })
                .catch((error) => {
                    console.error('Error fetching emails:', error);
                });
        },
        emailView(email) {
            const emailId = email.id;
            this.$router.push({ name: 'EmailView', params: { id: emailId } });
        },
        formatDate(dateStr) {
            const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
            return new Date(dateStr).toLocaleDateString('en-US', options);
        },
        deleteSelectedEmails() {
            const selectedEmailIds = this.emails.filter((email) => email.selected).map((email) => email.id);
            if (selectedEmailIds.length === 0) {
                return; // No selected emails to delete
            }
            const email = localStorage.getItem('email');
            const deleteRequests = selectedEmailIds.map((id) => {
                return axios.delete(`http://localhost:8080/users/delete/${email}/${id}`);
            });
            Promise.all(deleteRequests)
                .then((responses) => {
                    // Handle successful deletion here
                    console.log('Selected emails deleted successfully', responses);
                    // Remove deleted emails from the local list
                    this.emails = this.emails.filter((email) => !selectedEmailIds.includes(email.id));
                })
                .catch((error) => {
                    console.error('Error deleting selected emails:', error);
                });
        },
    },
    mounted() {
        this.fetchEmails();
    },
};
</script>

<style scoped>
.email-item {
    border: 1px solid #ccc;
    margin: 10px 0;
    padding: 10px;
    display: flex;
    flex-direction: row;
    align-items: center;
    border-radius: 10px;
}

.email-list {
    list-style: none;
    padding: 0;
}

.email-details {
    flex: 1;
}

.email-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.email-subject {
    margin-top: 5px;
}

.email-date {
    font-size: 0.8rem;
    color: #777;
}

.email-item.read {
    font-weight: normal;
}

.email-details {
    flex: 1;
}

.header {
    margin: 0;
    margin-bottom: 25px;
}

.part_1 {
    display: flex;
    justify-content: space-between;
}

.checkbox {
    margin-right: 20px;
}

.part_2 {
    display: flex;
    justify-content: space-between;
}

.eye {
    margin-left: 20px;
    margin-right: 10px;
}

.folder-dropdown {
    position: absolute;
    background-color: white;
    border: 1px solid #ccc;
    padding: 5px;
    border-radius: 5px;
    top: 25px;
    left: 0;
    z-index: 1;
    display: none;
}

.folders:hover .folder-dropdown {
    display: block;
}
.main-item {
  background-color: #f5f5f5;
  border-radius: 10px;
}
</style>