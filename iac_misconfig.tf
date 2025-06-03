aws" { 
  region = "us-east-1" 
} 

# 🚨 Insecure S3 Bucket: Public Access Enabled 
resource "aws_s3_bucket" "insecure_bucket" {
  bucket = "prisma-cloud-insecure-bucket"
  acl    = "public-read"  # ❌ This makes the bucket publicly accessible!
}

# 🚨 Open Security Group: Allows All Traffic
resource "aws_security_group" "open_sg" {
  name        = "open_security_group"
  description = "Allows all inbound traffic"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]  # ❌ This allows anyone to access any service
  }
} 

# 🚨 Unencrypted RDS Database
resource "aws_db_instance" "insecure_db" {
  identifier        = "prisma-insecure-db"
  instance_class    = "db.t3.micro"
  allocated_storage = 20
  engine           = "mysql"
  username        = "admin"
  password        = "SuperSecret123!"
  skip_final_snapshot = true

  storage_encrypted = false  # ❌ No encryption enabled
}
