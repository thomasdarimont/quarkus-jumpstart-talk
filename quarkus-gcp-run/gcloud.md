# Build native binary
mvn clean package -Pnative

# Build docker image with native binary
export IMAGE_NAME=thomasdarimont/quarkus-gcp
export GCR_IMAGE_NAME=eu.gcr.io/$IMAGE_NAME
export SERVICE_NAME=quarkus-gcp

docker build -f src/main/docker/Dockerfile.native-distroless -t $IMAGE_NAME .

# Push docker image to gcp docker registry
docker tag $IMAGE_NAME\:latest $GCR_IMAGE_NAME

docker push $GCR_IMAGE_NAME

# Tested with gcloud version >= 330

# Deploy service on Google Cloud Run
gcloud beta run deploy $SERVICE_NAME \
  --image $GCR_IMAGE_NAME \
  --platform managed \
  --region europe-west3 \
  --allow-unauthenticated \
  --min-instances=0 \
  --max-instances=1 

# Delete cloud run instance
gcloud beta run services delete $SERVICE_NAME \
  --platform managed \
  --region europe-west3 \
  --quiet

# Delete image
gcloud beta container images delete $GCR_IMAGE_NAME\:latest  --quiet