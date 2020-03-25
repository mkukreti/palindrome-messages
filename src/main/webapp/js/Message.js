'use strict';

/**
 * Message component representing a single message
 */
class Message extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showDetails: false,
      deleteStatus: null
    };
  }

  deleteMessage = (id) => {
    return fetch('rest/palindrome/messages/' + id, {
      method: 'delete'
    })
    .then(res => res.json())
  };

  editMessage = (id, newMessage) => {
    return fetch('rest/palindrome/messages/' + id + '/' + newMessage, {
      method: 'post'
    })
      .then(res => res.json())
  };

  deleteStatus = (status) => {
    this.setState({
      deleteStatus: status
    });
  };

  renderMessageDetails() {
    const { id, palindrome, timestamp } = this.props.message;
    if(this.state.showDetails) {
      return (
        <div id='message_details'>
          <div>
            { 'id: ' + id }
          </div>
          <div>
            { 'palindrome: ' + palindrome }
          </div>
          <div>
            { 'sent at: ' + timestamp }
          </div>
        </div>
      )
    }
  }

  handleDelete = () => {
    this.deleteStatus('DELETING');
    this.deleteMessage(this.props.message.id)
      .then((message) => { this.deleteStatus('DELETED'); this.props.onDelete(message);})
      .catch(() => this.deleteStatus('FAILED'))
  };

  handleShowDetails = () => {
    this.setState({
      showDetails: !this.state.showDetails
    });
  };

  renderMessage() {
    const { deleteStatus } = this.state;
    let containerClass = '';
    if(deleteStatus) {
      switch (deleteStatus) {
        case 'DELETING':
          containerClass += 'deleting';
          break;
        case 'DELETED':
          containerClass += 'deleted';
          break;
        case 'FAILED':
          containerClass += 'deleteFailed';
          break;
      }
    }
    const detailText = this.state.showDetails ? 'hide details' : 'show details';
    return (
      <div id='message' className={containerClass}>
        <div id='message_body'>
          {this.props.message.body}
        </div>
        <button disabled={deleteStatus != null || deleteStatus === 'FAILED'} onClick={this.handleDelete}>
          delete
        </button>
        <button onClick={this.handleShowDetails}>
          {detailText}
        </button>
        {this.renderMessageDetails()}
      </div>
    )
  }

  render() {
    return (
      <div id='message_container'>
        {this.renderMessage()}
      </div>
    )
  }
}