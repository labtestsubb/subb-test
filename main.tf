resource "aws_s3_bucket" "bad_bucket" {
  bucket = "prisma-cloud-test-bucket"
  acl    = "private"

  tags = {
    Name        = "PrismaCloudTestBucket"
    Environment = "Test"
  }
}


resource "aws_s3_bucket" "bad_bucket_log_bucket" {
  bucket = "bad_bucket-log-bucket"
}

resource "aws_s3_bucket_logging" "bad_bucket" {
  bucket = aws_s3_bucket.bad_bucket.id
  target_bucket = aws_s3_bucket.bad_bucket_log_bucket.id
  target_prefix = "log/"
}


#test123
resource "aws_security_group" "bad_security_group" {
  name        = "prisma-cloud-test-sg"
  description = "Allow all inbound traffic"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"] # This is a misconfiguration (open to the world)
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "PrismaCloudTestSG"
    Environment = "Test"
  }
}
