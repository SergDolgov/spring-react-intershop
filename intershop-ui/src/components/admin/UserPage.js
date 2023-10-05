import React, { useEffect, useState } from 'react'
import { NavLink, Navigate, useParams } from 'react-router-dom'
import { Button, Form, Grid, Segment, Message, Select, Label} from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { productApi } from '../misc/ProductApi'
import { parseJwt, handleLogError } from '../misc/Helpers'

function UserPage() {
  const Auth = useAuth()
  const admin = Auth.getUser()

  const {username} = useParams();

  const roles = [
   { key: 'a', text: 'Admin', value: 'ADMIN' },
   { key: 'u', text: 'User', value: 'USER' },
  ]

  const [isSaved, setSaved] = useState(false)

  const [user, setUser] = useState([])
  const [role, setRole] = useState('')
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [isError, setIsError] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')

  useEffect(() => {
    async function fetchData() {
      try {
          const response = await productApi.getUsers(admin, username)
          const data = response.data
          setUser(data)
          setName(data.name)
          setRole(data.role)
          setEmail(data.email)
      } catch (error) {
        handleLogError(error)
      }
    }
    fetchData()
  }, [])

  const handleInputChange = (e, { name, value }) => {
    if (name === 'username') {
      {
      //setUsername(value)
      }
    } else if (name === 'role') {
      setRole(value)
    } else if (name === 'name') {
      setName(value)
    } else if (name === 'email') {
      setEmail(value)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!(username && role && name && email)) {
      setIsError(true)
      setErrorMessage('Please, inform all fields!')
      return
    }

    const user = { username, role, name, email }

    try {
      const response = await productApi.updateUser(user)
     {/* const { accessToken } = response.data
      const data = parseJwt(accessToken)
      const authenticatedUser = { data, accessToken }

      Auth.userLogin(authenticatedUser)

      setUsername('')
      setRole('')
      setIsError(false)
      setErrorMessage('')
      */}
    } catch (error) {
      handleLogError(error)
      if (error.response && error.response.data) {
        const errorData = error.response.data
        let errorMessage = 'Invalid fields'
        if (errorData.status === 409) {
          errorMessage = errorData.message
        } else if (errorData.status === 400) {
          errorMessage = errorData.errors[0].defaultMessage
        }
        setIsError(true)
        setErrorMessage(errorMessage)
      }
    }
  }

  if (isSaved) {
    return <Navigate to='/adminpage' />
  }

  return (
    <Grid textAlign='center'>
      <Grid.Column style={{ maxWidth: 450 }}>
        <Form size='large' onSubmit={handleSubmit}>
          <Segment>
            <Form.Input
              label='Username'
              autoFocus
              name='username'
              icon='user'
              iconPosition='left'
              value={username}
              onChange={handleInputChange}
            />
            <Form.Input
              label='Role'
              control={Select}
              options={roles}
              name='role'
              value={role}
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              label='Name'
              name='name'
              icon='address card'
              iconPosition='left'
              value={name}
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              label='Email'
              name='email'
              icon='at'
              iconPosition='left'
              value={email}
              onChange={handleInputChange}
            />
            <Button color='purple' fluid size='small'>Update</Button>
          </Segment>
        </Form>

        {isError && <Message negative>{errorMessage}</Message>}
      </Grid.Column>
    </Grid>
  )
}

export default UserPage