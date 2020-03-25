'use strict';

/**
 * React app that provides a UI to read, post, delete and update messages
 */
class PalindromMessages extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: null,
      loading: false,
      newMessage: null
    };
  }

  componentDidMount() {
    this.loading(true);
    this.getMessages()
      .then((messages) => {
        this.setState({
          messages,
          loading: false
        });
      })
      .catch(() => this.loading(false));
  }

  getMessages = () => {
    return fetch('rest/palindrome/messages', {
      method: 'get'
    })
    .then(res => res.json())
  };

  addMessage = (message) => {
    return fetch('rest/palindrome/messages/' + message, {
      method: 'post'
    })
    .then(res => res.json())
  };

  loading = (isLoading) => {
    this.setState({
      loading: isLoading
    });
  };

  handleAddMessageChange = (e) => {
    this.setState({
      newMessage: e.target.value
    })
  };

  handleMessageSubmit = () => {
    this.addMessage(this.state.newMessage)
      .then((newMessage) => this.setState({
        messages: [...this.state.messages, newMessage]
      }))
  };

  handleDelete = (message) => {
    this.setState({
      messages: this.state.messages.filter(msg => msg.id !== message.id)
    });
  };

  renderExistingMessages() {
    const messages = this.state.messages;
    if(messages) {
      return (
        <div id='messages_container'>
          {
            messages.map(message => (
              <Message message={message} key={message.id} onDelete={this.handleDelete} />
            ))
          }
        </div>
      );
    }
    return null;
  }

  renderAddMessage() {
    return (
      <div id='add_message_container'>
        <textarea className='textBox' type="text" name="newMessage" onChange={this.handleAddMessageChange}/>
        <button className='send' type="submit" onClick={this.handleMessageSubmit}>Send</button>
      </div>
    );
  }

  renderLoading() {
    return (
      <div>Loading...</div>
    );
  }

  render() {
    if(this.state.loading) {
      return this.renderLoading();
    } else {
      return (
        <div>
          {this.renderExistingMessages()}
          {this.renderAddMessage()}
        </div>
      )
    }
  }
}

ReactDOM.render(<PalindromMessages />, document.getElementById('palindrome-messages-root'));