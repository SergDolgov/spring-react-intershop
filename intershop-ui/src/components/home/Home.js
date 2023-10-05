import React, { useState, useEffect } from 'react'
import { Statistic, Icon, Grid, Container, Image, Segment, Dimmer, Loader } from 'semantic-ui-react'
import { productApi } from '../misc/ProductApi'
import { handleLogError } from '../misc/Helpers'

function Home() {
  const [numberOfUsers, setNumberOfUsers] = useState(0)
  const [numberOfProducts, setNumberOfProducts] = useState(0)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    async function fetchData() {
      setIsLoading(true)
      try {
        let response = await productApi.numberOfUsers()
        const users = response.data

        response = await productApi.numberOfProducts()
        const products = response.data

        setNumberOfUsers(users)
        setNumberOfProducts(products)
      } catch (error) {
        handleLogError(error)
      } finally {
        setIsLoading(false)
      }
    }
    fetchData()
  }, [])

  if (isLoading) {
    return (
      <Segment basic style={{ marginTop: window.innerHeight / 2 }}>
        <Dimmer active inverted>
          <Loader inverted size='huge'>Loading</Loader>
        </Dimmer>
      </Segment>
    )
  }

  return (
    <Container text>
      <Grid stackable columns={3}>
        <Grid.Row>
          <Grid.Column textAlign='center'>
            <Segment color='purple'>
              <Statistic>
                <Statistic.Value><Icon name='user' color='grey' />{numberOfUsers}</Statistic.Value>
                <Statistic.Label>Users</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
          <Grid.Column textAlign='center'>
            <Segment color='purple'>
              <Statistic>
                <Statistic.Value><Icon name='laptop' color='grey' />{numberOfProducts}</Statistic.Value>
                <Statistic.Label>Products</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
            <Grid.Column textAlign='center'>
                    <Segment color='purple'>
                      <Statistic>
                        <Statistic.Value><Icon name='user' color='grey' />{numberOfProducts}</Statistic.Value>
                        <Statistic.Label>Products</Statistic.Label>
                      </Statistic>
                    </Segment>
                  </Grid.Column></Grid.Row>
      </Grid>

      <Image src='https://react.semantic-ui.com/images/wireframe/media-paragraph.png' style={{ marginTop: '2em' }} />
      <Image src='https://react.semantic-ui.com/images/wireframe/paragraph.png' style={{ marginTop: '2em' }} />
    </Container>
  )
}

export default Home